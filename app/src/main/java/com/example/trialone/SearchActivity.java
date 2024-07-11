package com.example.trialone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.trialone.Adapter.FullAdapter;
import com.example.trialone.Adapter.PopularAdapter;
import com.example.trialone.domain.FoodDomain;
import com.example.trialone.domain.FullDomain;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter3;
    private RecyclerView recyclerViewAllFoodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getWindow().setStatusBarColor(Color.parseColor("#ffffff"));
        recyclerViewAllFood();
        bottomNavigation();

        String searchText = getIntent().getStringExtra("searchText");
        EditText searchView = findViewById(R.id.searchView);
        searchView.setText(searchText);
    }
    private void bottomNavigation() {
        ImageView kartButton = findViewById(R.id.kartButton);
        ImageView homeButton = findViewById(R.id.homeBtn);

        kartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchActivity.this, CartListActivity.class));
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchActivity.this, MainActivity.class));
            }
        });
    }

    private void recyclerViewAllFood() {

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewAllFoodList=findViewById(R.id.rv3);
        recyclerViewAllFoodList.setLayoutManager(linearLayoutManager);

        ArrayList<FullDomain>fullList=new ArrayList<>();
        fullList.add(new FullDomain("chicken biriyani.(F)", "cat_13", "This is a special Biriyani made by Abdullah with his own hands.", 100.0));
        fullList.add(new FullDomain("chicken biriyani.(H)", "cat_13", "This is a special Biriyani made by Abdullah with his own hands.", 80.0));
        fullList.add(new FullDomain("chicken biriyani.(mini)", "cat_13", "This is a special Biriyani made by Abdullah with his own hands.", 70.0));
        fullList.add(new FullDomain("biriyani extra rice.(H)", "cat_13", "This is a special Biriyani made by Abdullah with his own hands.", 30));
        fullList.add(new FullDomain("biriyani extra rice.(F)", "cat_13", "This is a special Biriyani made by Abdullah with his own hands.", 60.0));


        adapter3=new FullAdapter(fullList);
        recyclerViewAllFoodList.setAdapter(adapter3);

        EditText searchView=findViewById(R.id.searchView);

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                ((FullAdapter) adapter3).filter(s.toString());
            }
        });
    }
}