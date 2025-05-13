package com.example.kursach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterRequests extends RecyclerView.Adapter<AdapterRequests.MyHolder>{
    Context context;
    List<ModelRequest> requestList;
    public AdapterRequests(Context context, List<ModelRequest> requestList){
        this.context = context;
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_group_users, parent, false);
        Button aproveBtn = view.findViewById(R.id.aproveButton);
        TextView buffer = view.findViewById(R.id.phoneTV);
        buffer.setVisibility(View.GONE);
        aproveBtn.setVisibility(View.VISIBLE);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String userName = requestList.get(position).getUserName();
        String groupName = "Запрос на вступление в " + requestList.get(position).getGroupName();
        String userImage = requestList.get(position).getUserImage();

        holder.mNameTv.setText(userName);
        holder.mGroup.setText(groupName);
        try{
            Picasso.get().load(userImage).placeholder(R.drawable.ic_default_group).into(holder.mAvatarIv);
        }
        catch(Exception e){

        }

        holder.mAproveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users"); // <---
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds: snapshot.getChildren()){
                            ModelUser modelUser = ds.getValue(ModelUser.class);
                            if (holder.mNameTv.getText().toString().equals(modelUser.getName())) {
                                if(!modelUser.getGroups().equals(holder.mGroup.getText().toString())){
                                    String uid = modelUser.getUid();
                                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(uid);
                                    userRef.child("groups").setValue(modelUser.getGroups()+groupName.replace("Запрос на вступление в ", ", "))
                                            .addOnSuccessListener(aVoid -> {
                                                Toast.makeText(context, "Группа обновлена", Toast.LENGTH_SHORT).show();
                                            })
                                            .addOnFailureListener(e -> {
                                                Toast.makeText(context, "Ошибка обновления", Toast.LENGTH_SHORT).show();
                                            });
                                    DatabaseReference requestsRef = FirebaseDatabase.getInstance().getReference("Requests");
                                    requestsRef.child(uid).removeValue();
                                    break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(context, "Ошибка доступа к базе", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        ImageView mAvatarIv;
        TextView mNameTv, mGroup;
        Button mAproveBtn;
        public MyHolder(@NonNull View itemView){
            super(itemView);
            mAproveBtn = itemView.findViewById(R.id.aproveButton);
            mAvatarIv = itemView.findViewById(R.id.avatarIv);
            mNameTv = itemView.findViewById(R.id.nameTv);
            mGroup = itemView.findViewById(R.id.emailTv);
        }
    }
}
