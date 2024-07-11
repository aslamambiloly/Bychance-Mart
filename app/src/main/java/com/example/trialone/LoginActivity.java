package com.example.trialone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.trialone.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class LoginActivity extends BaseActivity {
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setVariable();
        getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));

    }

    private void setVariable() {
            binding.signinBtn.setOnClickListener(v -> {
                String email=binding.emailEdt.getText().toString().trim();
                String password=binding.passEdt.getText().toString();
                if (!email.isEmpty() && !password.isEmpty()){
                    binding.progressBar1.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, task -> {
                        if (task.isSuccessful()){
                            startActivity(new Intent(LoginActivity.this,ProfileInput.class));
                        }else {
                            Toast.makeText(LoginActivity.this,"Authentication failed",Toast.LENGTH_SHORT).show();
                        }
                        binding.progressBar1.setVisibility(View.INVISIBLE);
                    });
                }else {
                    Toast.makeText(LoginActivity.this, "please fill your username and password", Toast.LENGTH_SHORT).show();
                }

            });

        binding.signup.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this,SignupActivity.class)));


    }
}