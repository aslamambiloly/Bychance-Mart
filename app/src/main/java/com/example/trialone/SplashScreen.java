package com.example.trialone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class SplashScreen extends BaseActivity {

    Animation topAnim, bottomAnim, sideAnim;
    ImageView topImg1, sideImg1, bottomImg1;
    ProgressBar progressBar6;
    private static int SPLASH_SCREEN=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);



        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim=AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        sideAnim=AnimationUtils.loadAnimation(this,R.anim.side_animation);

        topImg1=findViewById(R.id.imgTop1);
        sideImg1=findViewById(R.id.imgSide1);
        bottomImg1=findViewById(R.id.imgBottom1);
        progressBar6=findViewById(R.id.progressBar6);

        topImg1.startAnimation(topAnim);
        sideImg1.startAnimation(sideAnim);
        bottomImg1.startAnimation(bottomAnim);
        getWindow().setStatusBarColor(Color.parseColor("#E8C290"));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar6.setVisibility(View.VISIBLE);
                if (mAuth.getCurrentUser() !=null){
                    startActivity(new Intent(SplashScreen.this,MainActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(SplashScreen.this,introActivity.class));
                    finish();
                }
            }
        },SPLASH_SCREEN);

    }
}