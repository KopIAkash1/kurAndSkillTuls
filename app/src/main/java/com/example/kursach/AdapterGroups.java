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

public class AdapterGroups extends RecyclerView.Adapter<AdapterGroups.MyHolder> {
    Context context;
    List<ModelGroups> groupsList;

    public AdapterGroups(Context context, List<ModelGroups> groupsList){
        this.context = context;
        this.groupsList = groupsList;
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_groups, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String groupImage = groupsList.get(position).getImage();
        String groupName = groupsList.get(position).getName();
        String groupSkill = groupsList.get(position).getSkill();

        holder.mNameTv.setText(groupName);
        holder.mSkillTv.setText(groupSkill);
        try{
            Picasso.get().load(groupImage).placeholder(R.drawable.ic_default_group).into(holder.mAvatarIv);
        }
        catch(Exception e){

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Toast.makeText(context, ""+groupName,Toast.LENGTH_SHORT).show();
                GroupFragment groupFragment = new GroupFragment();
                Bundle args = new Bundle();
                args.putString("groupNameKey", groupName);

                groupFragment.setArguments(args);

                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content, groupFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return groupsList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        ImageView mAvatarIv;
        TextView mNameTv, mSkillTv;
        public MyHolder(@NonNull View itemView){
            super(itemView);

            mAvatarIv = itemView.findViewById(R.id.avatarIv);
            mNameTv = itemView.findViewById(R.id.nameTv);
            mSkillTv = itemView.findViewById(R.id.skillTv);

        }
    }
}
