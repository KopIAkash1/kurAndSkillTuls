package com.example.kursach;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.MyHolder> {
    Context context;
    List<ModelUser> usersList;

    public AdapterUsers(Context context, List<ModelUser> userList){
        this.context = context;
        this.usersList = userList;
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
        try{
            Picasso.get().load(image).placeholder(R.drawable.ic_default_group).into(holder.mAvatarIv);
        }
        catch(Exception e){

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Toast.makeText(context, ""+name,Toast.LENGTH_SHORT).show();
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
        public MyHolder(@NonNull View itemView){
            super(itemView);

            mAvatarIv = itemView.findViewById(R.id.avatarIv);
            mNameTv = itemView.findViewById(R.id.nameTv);
            mEmailTv = itemView.findViewById(R.id.emailTv);
            mPhoneTv = itemView.findViewById(R.id.phoneTV);

        }
    }
}
