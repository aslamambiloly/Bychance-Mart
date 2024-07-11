package com.example.trialone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


import com.example.trialone.databinding.ActivitySignupBinding;

public class SignupActivity extends BaseActivity {
    ActivitySignupBinding binding;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setVariable();
        getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));


    }

    private void setVariable() {
        binding.signupBtn.setOnClickListener(v -> {
            String email=binding.emailEdt.getText().toString().trim();
            String password=binding.passEdt.getText().toString().trim();

            if(password.length()<8){
                Toast.makeText(SignupActivity.this,"your password must include atleast 8 characters ", Toast.LENGTH_SHORT).show();
                return;
            }
            binding.progressBar1.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignupActivity.this, task -> {
                if (task.isSuccessful()){
                    Log.i(TAG,"welcome to family");
                    startActivity(new Intent(SignupActivity.this,ProfileInput.class));
                }else {
                    Log.i(TAG,"failure :"+task.getException());
                    Toast.makeText(SignupActivity.this,"Authentication failed, try signing in",Toast.LENGTH_SHORT).show();
                }
                binding.progressBar1.setVisibility(View.INVISIBLE);
            });
        });

        binding.signin.setOnClickListener(v -> startActivity(new Intent(SignupActivity.this,LoginActivity.class)));
    }
}