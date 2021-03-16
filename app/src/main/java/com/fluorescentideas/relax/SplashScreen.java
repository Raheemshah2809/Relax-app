package com.fluorescentideas.relax;



import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.dmoral.toasty.Toasty;

public class SplashScreen extends AppCompatActivity{


    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Toasty.info(SplashScreen.this, "Welcome to Relax. Your Personal Assistant App", Toast.LENGTH_LONG).show();
        Toasty.info(SplashScreen.this, "From Here You Can Set A timer", Toast.LENGTH_LONG).show();
        Toasty.info(SplashScreen.this, "Check The Weather Were You Are Or Around The World", Toast.LENGTH_LONG).show();
        Toasty.info(SplashScreen.this, "Play Ambient Music", Toast.LENGTH_LONG).show();
        Toasty.info(SplashScreen.this, "Set Notes", Toast.LENGTH_LONG).show();
        Toasty.info(SplashScreen.this, "And Reminders", Toast.LENGTH_LONG).show();
        Toasty.info(SplashScreen.this, "To Get Started Click On Any of the Tasks You Want Me To Do", Toast.LENGTH_LONG)           .show();
        Toasty.error(SplashScreen.this, "Currently the Dashboard Is Not Out In This Version", Toast.LENGTH_LONG).show();
        Toasty.warning(SplashScreen.this, "App Version Number 1.4.1", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();


    }
}