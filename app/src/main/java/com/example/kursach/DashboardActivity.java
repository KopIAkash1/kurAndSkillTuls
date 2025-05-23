package com.example.kursach;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    TextView profileName;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Профиль");
        }
        mAuth = FirebaseAuth.getInstance();
        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);

        actionBar.setTitle("Главная");
        HomeFragment fragment1 = new HomeFragment();
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.content, fragment1,"");
        ft1.commit();
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener selectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if (item.getItemId() == R.id.nav_home){
                        actionBar.setTitle("Главная");
                        HomeFragment fragment1 = new HomeFragment();
                        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                        ft1.replace(R.id.content, fragment1,"");
                        ft1.commit();
                        return true;
                    } else if (item.getItemId() == R.id.nav_profile){
                        actionBar.setTitle("Профиль");
                        ProfileFragment fragment2 = new ProfileFragment();
                        FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                        ft2.replace(R.id.content, fragment2,"");
                        ft2.commit();
                        return true;
                    }
                    else if (item.getItemId() == R.id.nav_friends){
                        actionBar.setTitle("Сообщества");
                        FriendsFragment fragment3 = new FriendsFragment();
                        FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                        ft3.replace(R.id.content, fragment3,"");
                        ft3.commit();
                        return true;
                    }
                    return false;
                }
            };

    private void checkUserStatus(){
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            // user is signed
            //profileName.setText(user.getEmail());
        }
        else {
            // user is not signed
            startActivity(new Intent(DashboardActivity.this, MainActivity.class));
            finish();
        }
    }
    @Override
    protected void onStart(){
        checkUserStatus();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout){
            mAuth.signOut();
            checkUserStatus();
        }
        if (id == R.id.action_add_post){
            startActivity(new Intent(this, AddPostActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}