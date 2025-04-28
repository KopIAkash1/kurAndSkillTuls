package com.example.kursach;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
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

public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.MyHolder> {
    Context context;
    List<ModelUser> usersList;
    String groupName;

    public AdapterUsers(Context context, List<ModelUser> userList, String groupName){
        this.context = context;
        this.usersList = userList;
        this.groupName = groupName;
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_group_users, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String email = usersList.get(position).getEmail();
        String name = usersList.get(position).getName();
        String image = usersList.get(position).getImage();
        String phone = usersList.get(position).getPhone();

        holder.mNameTv.setText(name);
        holder.mEmailTv.setText(email);
        holder.mPhoneTv.setText(phone);
        holder.mRemoveBtn.setText("Удалить");
        holder.mRemoveBtn.setVisibility(View.VISIBLE);
        try{
            Picasso.get().load(image).placeholder(R.drawable.ic_default_group).into(holder.mAvatarIv);
        }
        catch(Exception e){

        }

        holder.mRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users"); // <---
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            ModelUser modelUser = ds.getValue(ModelUser.class);
                            if (holder.mNameTv.getText().toString().equals(modelUser.getName())) {
                                String uid = modelUser.getUid();
                                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(uid);
                                userRef.child("groups").setValue(modelUser.getGroups().replace(groupName, ""))
                                        .addOnSuccessListener(aVoid -> {
                                            Toast.makeText(context, "Пользователь удален из группы", Toast.LENGTH_SHORT).show();
                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(position,usersList.size());
                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(context, "Ошибка удаления", Toast.LENGTH_SHORT).show();
                                        });
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        ImageView mAvatarIv;
        TextView mNameTv, mEmailTv, mPhoneTv;
        Button mRemoveBtn;
        public MyHolder(@NonNull View itemView){
            super(itemView);
            mRemoveBtn = itemView.findViewById(R.id.aproveButton);
            mAvatarIv = itemView.findViewById(R.id.avatarIv);
            mNameTv = itemView.findViewById(R.id.nameTv);
            mEmailTv = itemView.findViewById(R.id.emailTv);
            mPhoneTv = itemView.findViewById(R.id.phoneTV);

        }
    }
}
