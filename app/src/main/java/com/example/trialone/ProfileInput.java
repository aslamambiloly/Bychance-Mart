package com.example.trialone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trialone.databinding.ActivityProfileInputBinding;
import com.example.trialone.databinding.ActivitySignupBinding;

public class ProfileInput extends AppCompatActivity {

    private TextView name,phone;
    private Button okay,signin;
    private ProgressBar bar;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_input);

        getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));


        name=findViewById(R.id.userName);
        phone=findViewById(R.id.phoneNumber);
        okay=findViewById(R.id.okayBtn);
        signin=findViewById(R.id.sign);
        bar=findViewById(R.id.profileBar);
        usersRef = FirebaseDatabase.getInstance().getReference().child("users");

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                String username=name.getText().toString().toLowerCase();
                String phonenumber=phone.getText().toString();

                if(username.length()>10) {
                    Toast.makeText(ProfileInput.this, "sorry pal, all we need is a name with not more than 10 letters", Toast.LENGTH_SHORT).show();
                    return;
                }

                DatabaseReference userRef = usersRef.push();
                userRef.child("username").setValue(username);
                userRef.child("phonenumber").setValue(phonenumber);

                saveUsernameToSharedPreferences(username);

                Intent intent=new Intent(ProfileInput.this,MainActivity.class);
                startActivity(intent);

            }

        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
    }

    private void saveUsernameToSharedPreferences(String username){
        SharedPreferences sharedPreferences=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("username",username);
        editor.apply();
    }
}
