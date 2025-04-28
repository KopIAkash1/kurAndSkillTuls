package com.example.kursach;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String groups;
    RecyclerView recyclerView;
    List<ModelPost> postsList;
    AdapterPosts adapterPosts;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, userDbRef;
    ImageView avatarIv;
    TextView nameTv, skillTv, usersTv;
    Button requestButton, addPostButton;
    ModelUser modelUser;
    String groupForSearchName;
    String image;
    public GroupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Group.
     */
    // TODO: Rename and change types and number of parameters
    public static GroupFragment newInstance(String param1, String param2) {
        GroupFragment fragment = new GroupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            groupForSearchName = getArguments().getString("groupNameKey");
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Groups");

        avatarIv = view.findViewById(R.id.avatarIv);
        nameTv = view.findViewById(R.id.nameTv);
        skillTv = view.findViewById(R.id.skillTv);
        usersTv = view.findViewById(R.id.usersTv);
        requestButton = view.findViewById(R.id.requestButton);
        addPostButton = view.findViewById(R.id.addPostButton);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap <Object,String> data = new HashMap<>();
                data.put("groupName", groupForSearchName);
                data.put("userName", modelUser.getName());
                data.put("userImage", modelUser.getImage());
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Requests");
                ref.child(modelUser.getUid()).setValue(data).addOnSuccessListener(unused ->
                        Toast.makeText(getContext(), "Данные отправлены", Toast.LENGTH_SHORT).show()
                ).addOnFailureListener(e ->
                        Toast.makeText(getContext(), "Ошибка отправки запроса", Toast.LENGTH_SHORT).show()
                );
            }
        });

        usersTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupUsersFragment groupFragment = new GroupUsersFragment();
                Bundle args = new Bundle();
                args.putString("groupNameKey", nameTv.getText().toString());

                groupFragment.setArguments(args);

                FragmentManager fragmentManager = ((AppCompatActivity)getActivity()).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content, groupFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        userDbRef = FirebaseDatabase.getInstance().getReference("Users");
        Query query = userDbRef.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    modelUser = ds.getValue(ModelUser.class);
                    if (modelUser.getGroups().contains(groupForSearchName)){
                        requestButton.setVisibility(View.GONE);
                    }
                    if (modelUser.getGroups().contains("Админы")){
                        addPostButton.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // ЗАПРОС
        query = databaseReference.orderByChild("name");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    String GroupName = "" + ds.child("name").getValue();
                    if (GroupName.equals(groupForSearchName)) {
                        String name = "" + ds.child("name").getValue();
                        String skill = "" + ds.child("skill").getValue();
                        image = "" + ds.child("image").getValue();

                        nameTv.setText(name);
                        skillTv.setText(skill);

                        try {
                            Picasso.get().load(image).into(avatarIv);
                        } catch (Exception e) {
                            Picasso.get().load(R.drawable.ic_add_image).into(avatarIv);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddPostCommunityActivity.class);
                intent.putExtra("groupName",nameTv.getText().toString());
                intent.putExtra("groupDp",image);
                startActivity(intent);
            }
        });

        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        recyclerView.setLayoutManager(layoutManager);


        postsList = new ArrayList<>();
        loadPosts();
        return view;
    }
    private void loadPosts(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postsList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    ModelPost modelPost = ds.getValue(ModelPost.class);
                    if (modelPost.uName.equals(nameTv.getText().toString())) {
                        postsList.add(modelPost);
                        adapterPosts = new AdapterPosts(getActivity(), postsList);
                        recyclerView.setAdapter(adapterPosts);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}