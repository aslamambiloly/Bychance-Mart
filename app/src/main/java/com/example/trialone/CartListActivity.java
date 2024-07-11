package com.example.trialone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trialone.Adapter.CartListAdapter;
import com.example.trialone.Helper.ManagementCart;
import com.example.trialone.Interface.ChangeNumberItemsListener;
import com.example.trialone.MainActivity;
import com.example.trialone.R;
import com.example.trialone.domain.FoodDomain;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CartListActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ManagementCart managementCart;
    private TextView totalFeetxt, additionalFeetxt, deliverFeeTxt, totalTxt, emptyTxt, loading, loadinghint, phone2,address2,name2;
    private double tax;
    private ImageView emptyImg;
    private EditText nameTxt, phoneTxt, addressTxt;
    private Button btn;
    private FirebaseFirestore db;
    private ScrollView scrollView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        managementCart = new ManagementCart(this);
        db = FirebaseFirestore.getInstance();
        initView();
        initList();
        CalculateCart();
        bottomNavigation();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkout();
                clearCart();
            }
        });
    }



    private void bottomNavigation(){
        ImageView kartButton=findViewById(R.id.kartButton);
        ImageView homeButton=findViewById(R.id.homeBtn);

        kartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartListActivity.this,CartListActivity.class));
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartListActivity.this, MainActivity.class));
            }
        });
    }

    private void initView() {
        recyclerViewList=findViewById(R.id.recyclerViewCart);
        totalFeetxt=findViewById(R.id.totalFee);
        additionalFeetxt=findViewById(R.id.serviceFee);
        deliverFeeTxt=findViewById(R.id.deliveryCharge);
        totalTxt=findViewById(R.id.totalAmount);
        emptyTxt=findViewById(R.id.emptyTxt);
        scrollView=findViewById(R.id.scrollView3);
        recyclerViewList=findViewById(R.id.recyclerViewCart);
        emptyImg=findViewById(R.id.emptyImg);
        nameTxt=findViewById(R.id.name);
        phoneTxt=findViewById(R.id.phoneNmbr);
        addressTxt=findViewById(R.id.address);
        btn=findViewById(R.id.checkout);
        loading=findViewById(R.id.loading);
        loadinghint=findViewById(R.id.loadinghint);
        progressBar=findViewById(R.id.progressBar2);
        name2=findViewById(R.id.name2);
        address2=findViewById(R.id.address2);
        phone2=findViewById(R.id.phone2);
    }

    private void initList(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter=new CartListAdapter(managementCart.getListCart(),this,new ChangeNumberItemsListener(){
            @Override
            public void changed(){
                CalculateCart();
            }
        });

        recyclerViewList.setAdapter(adapter);
        if (managementCart.getListCart().isEmpty()){
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
            btn.setVisibility(View.GONE);
            emptyImg.setVisibility(View.VISIBLE);
        }else{
            emptyTxt.setVisibility(View.INVISIBLE);
            scrollView.setVisibility(View.VISIBLE);
            emptyImg.setVisibility(View.INVISIBLE);
        }
    }
    private void CalculateCart(){
        double percentTax=0.04;
        double delivery=20;

        tax=Math.round((managementCart.getTotalFee()*percentTax)*100)/100;
        double total=Math.round((managementCart.getTotalFee()+tax+delivery)*100)/100;
        double itemTotal=Math.round(managementCart.getTotalFee()*100)/100;

        totalFeetxt.setText("₹"+itemTotal);
        additionalFeetxt.setText("₹"+tax);
        deliverFeeTxt.setText("₹"+delivery);
        totalTxt.setText("₹"+total);

    }
    private void checkout() {

        String name = nameTxt.getText().toString().trim();
        String phone = phoneTxt.getText().toString().trim();
        String address = addressTxt.getText().toString().trim();
        double totalAmount = managementCart.getTotalFee() + tax;

        if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);
        loadinghint.setVisibility(View.VISIBLE);
        nameTxt.setVisibility(View.INVISIBLE);
        phoneTxt.setVisibility(View.INVISIBLE);
        addressTxt.setVisibility(View.INVISIBLE);
        phone2.setVisibility(View.INVISIBLE);
        address2.setVisibility(View.INVISIBLE);
        name2.setVisibility(View.INVISIBLE);

        Map<String, Object> orderDetails = new HashMap<>();
        orderDetails.put("name", name);
        orderDetails.put("phone", phone);
        orderDetails.put("address", address);
        orderDetails.put("totalAmount", totalAmount);
        orderDetails.put("timestamp", new Timestamp(new Date()));

        for (FoodDomain food : managementCart.getListCart()) {
            Map<String, Object> item = new HashMap<>();
            item.put("title", food.getTitle());
            item.put("quantity", food.getNumberInCart());
            item.put("totalPrice", food.getNumberInCart() * food.getFee());
            orderDetails.put(food.getTitle(), item);
        }

        db.collection("orders")
                .add(orderDetails)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(CartListActivity.this, "Order placed successfully", Toast.LENGTH_SHORT).show();
                    finish();
                    progressBar.setVisibility(View.INVISIBLE);
                    loading.setVisibility(View.INVISIBLE);
                    loadinghint.setVisibility(View.INVISIBLE);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(CartListActivity.this, "Failed to place order", Toast.LENGTH_SHORT).show();
                    nameTxt.setVisibility(View.VISIBLE);
                    phoneTxt.setVisibility(View.VISIBLE);
                    addressTxt.setVisibility(View.VISIBLE);
                    phone2.setVisibility(View.VISIBLE);
                    address2.setVisibility(View.VISIBLE);
                    name2.setVisibility(View.VISIBLE);
                });
    }

    private void clearCart() {
        managementCart.clearCart();
        adapter.notifyDataSetChanged();
        CalculateCart();
    }
}
