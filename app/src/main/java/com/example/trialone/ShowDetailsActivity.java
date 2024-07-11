package com.example.trialone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.trialone.Helper.ManagementCart;
import com.example.trialone.domain.FoodDomain;
import com.example.trialone.domain.FullDomain;

public class ShowDetailsActivity extends AppCompatActivity {

    private TextView addToCartBtn;
    private TextView titleTxt,feeTxt,descriptionTxt,numberOrderTxt,plusBtn,minusBtn;
    private ImageView foodImg;
    private FoodDomain foodObject;
    private FullDomain fullObject;
    int numberOrder=1;
    private ManagementCart managementCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        getWindow().setStatusBarColor(Color.parseColor("#d39a7c"));

        initView();
        getBundle();
        bottomNavigation();
        managementCart=new ManagementCart(this);
    }

    private void bottomNavigation() {
        ImageView kartButton = findViewById(R.id.kartButton);
        ImageView homeButton = findViewById(R.id.homeBtn);

        kartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowDetailsActivity.this, CartListActivity.class));
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowDetailsActivity.this, MainActivity.class));
            }
        });
    }

    private void initView() {
        addToCartBtn = findViewById(R.id.addToCartBtn);
        titleTxt = findViewById(R.id.titleTxt);
        feeTxt = findViewById(R.id.feeTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        numberOrderTxt = findViewById(R.id.numberOrderTxt);
        plusBtn = findViewById(R.id.plusCardBtn);
        minusBtn = findViewById(R.id.minusCardBtn);
        foodImg = findViewById(R.id.imgFood);
    }

    private void getBundle() {

        Object receivedObject=getIntent().getSerializableExtra("object");
        if (receivedObject instanceof FoodDomain) {
            foodObject=(FoodDomain) receivedObject;
            // Handle FoodDomain object
            int drawableResourceId = this.getResources().getIdentifier(foodObject.getImg(), "drawable", this.getPackageName());
            Glide.with(this)
                    .load(drawableResourceId)
                    .into(foodImg);

            titleTxt.setText(foodObject.getTitle());
            feeTxt.setText("₹" + foodObject.getFee());
            descriptionTxt.setText(foodObject.getDescription());
            numberOrderTxt.setText(String.valueOf(numberOrder));


            plusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    numberOrder = numberOrder + 1;
                    numberOrderTxt.setText(String.valueOf(numberOrder));
                }
            });

            minusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (numberOrder > 1) {
                        numberOrder = numberOrder - 1;

                    }
                    numberOrderTxt.setText(String.valueOf(numberOrder));
                }
            });

            addToCartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    foodObject.setNumberInCart(numberOrder);
                    managementCart.insertFood(foodObject);
                    finish();
                }
            });
        } else if (receivedObject instanceof FullDomain) {
            fullObject = (FullDomain) receivedObject;
            // Handle FullDomain object
            int drawableResourceId = this.getResources().getIdentifier(fullObject.getImg(), "drawable", this.getPackageName());
            Glide.with(this)
                    .load(drawableResourceId)
                    .into(foodImg);

            titleTxt.setText(fullObject.getTitle());
            feeTxt.setText("₹" + fullObject.getFee());
            descriptionTxt.setText(fullObject.getDescription());
            numberOrderTxt.setText(String.valueOf(numberOrder));

            plusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    numberOrder = numberOrder + 1;
                    numberOrderTxt.setText(String.valueOf(numberOrder));
                }
            });

            minusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (numberOrder > 1) {
                        numberOrder = numberOrder - 1;
                    }
                    numberOrderTxt.setText(String.valueOf(numberOrder));
                }
            });

            addToCartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fullObject.setNumberInCart(numberOrder);
                    managementCart.insertFood(fullObject);
                    finish();
                }
            });
        }
    }
}