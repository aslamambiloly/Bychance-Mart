package com.example.trialone;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.trialone.Adapter.CategoryAdapter;
import com.example.trialone.Adapter.PopularAdapter;
import com.example.trialone.domain.CategoryDomain;
import com.example.trialone.domain.FoodDomain;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

public class MainActivity extends ProfileInput {
    private RecyclerView.Adapter adapter, adapter2;
    private RecyclerView recyclerViewCategoryList, recyclerViewPopularList;
    private TextView name;
    private DatabaseReference usersRef;
    private FirebaseAuth mAuth;
    ImageView dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference().child("users");

        recyclerViewCategory();
        recyclerViewPopular();
        bottomNavigation();
        helloUser();
        checkAndShowDialog();
        getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
    }

    private void helloUser() {
        name = findViewById(R.id.hiName);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);

        if (username != null) {
            name.setText("hello " + username.toLowerCase()+"." );
        } else {


            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            usersRef.child(userId).child("username").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String username = snapshot.getValue(String.class);
                        name.setText("Hello " + username+".");
                        saveUsername(username);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e(TAG, "Failed to read user's name from Firebase.", error.toException());
                }
            });

        }
        dp=findViewById(R.id.dp);
    }

    private void saveUsername(String username) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.apply();
    }

    private void bottomNavigation() {
        ImageView kartButton = findViewById(R.id.kartButton);
        ImageView homeButton = findViewById(R.id.homeBtn);

        kartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CartListActivity.class));
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });
    }

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList = findViewById(R.id.recyclerView);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        ArrayList<CategoryDomain> category = new ArrayList<>();
        category.add(new CategoryDomain("pizza", "pizzanew"));
        category.add(new CategoryDomain("thali", "thali"));
        category.add(new CategoryDomain("biriyani", "cat_12"));
        category.add(new CategoryDomain("drinks", "cat_12"));
        category.add(new CategoryDomain("biriyani", "cat_12"));

        adapter = new CategoryAdapter(category);
        recyclerViewCategoryList.setAdapter(adapter);
    }

    private void recyclerViewPopular() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopularList = findViewById(R.id.recyclerView2);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        ArrayList<FoodDomain> foodList = new ArrayList<>();
        foodList.add(new FoodDomain("chicken biriyani.", "cat_13", "abdulla's special chicken biriyani is one of the best chicken biriyani which is currently available in lalpur. ", 100.0));
        foodList.add(new FoodDomain("egg roll. ", "eggroll1", "egg roll is a super instant hungry remover which is very tasty also.", 30.0));
        foodList.add(new FoodDomain("c", "cat_13", "aaa", 10.1));
        foodList.add(new FoodDomain("d", "cat_13", "aaa", 10.1));
        foodList.add(new FoodDomain("e", "cat_13", "aaa", 10.1));
        foodList.add(new FoodDomain("f", "cat_13", "aaa", 10.1));
        foodList.add(new FoodDomain("g", "cat_13", "aaa", 10.1));
        foodList.add(new FoodDomain("h", "cat_13", "aaa", 10.1));

        adapter2 = new PopularAdapter(foodList);
        recyclerViewPopularList.setAdapter(adapter2);

        TextView searchView = findViewById(R.id.searchView);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }
        });
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                ((PopularAdapter) adapter2).filter(s.toString());
            }
        });
    }

    private void checkAndShowDialog() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        boolean isFirstStart = sharedPreferences.getBoolean("isFirstStart", true);

        if (isFirstStart) {
            dialogFunction();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFirstStart", false);
            editor.apply();
        }
    }

    private void dialogFunction(){
        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.activity_dialog_one);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;  // Set the gravity if needed, e.g., bottom of the screen
        dialog.getWindow().setAttributes(params);
        dialog.show();

        Button btnOkay=dialog.findViewById(R.id.startBtn3);
        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
