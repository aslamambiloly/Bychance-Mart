package com.example.trialone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import com.example.trialone.databinding.ActivityIntroBinding;

public class introActivity extends AppCompatActivity {
    ActivityIntroBinding binding;
    Animation botAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setVariable();
        getWindow().setStatusBarColor(Color.parseColor("#b99976"));

        botAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation2);

        binding.imageViewOne.startAnimation(botAnim);
    }

    private void setVariable() {
        binding.startBtn.setOnClickListener(v -> {
            binding.progressBar3.setVisibility(View.VISIBLE);
            Intent intent=new Intent(introActivity.this,SignupActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
