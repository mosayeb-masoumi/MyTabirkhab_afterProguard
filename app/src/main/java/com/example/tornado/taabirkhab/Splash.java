package com.example.tornado.taabirkhab;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

    View view;
    Intent intent;
    ImageView imageViewSplash;
    LinearLayout linearLayout;
    Thread SplashThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);







        final Timer myTimer = new Timer();
        final View myview = getWindow().getDecorView().findViewById(
                android.R.id.content);
        myTimer.schedule(new TimerTask()
        {             int color = 0;
            boolean checkRGB=true;
            int min = 4;
            int max = 255;
            Random r = new Random();
            int green = r.nextInt(max - min + 1) + min;
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        if(checkRGB==true)
                            color++;
                        else{color--;}
                        myview.setBackgroundColor(Color.rgb(255, green, color));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Window window = getWindow();
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            window.setStatusBarColor(Color.rgb(255, green, color));
                        }                         if(color==255){checkRGB=false;}else if(color==0){checkRGB=true;}
                    }
                });
            }
        }, 0 ,10);













        imageViewSplash = (ImageView) findViewById(R.id.imagSplash);
        linearLayout = (LinearLayout) findViewById(R.id.lin_lay);
        startAnimations();
    }

    private void startAnimations() {
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.alpha);
        Animation translate = AnimationUtils.loadAnimation(this, R.anim.translate);

        rotate.reset();
        translate.reset();
        linearLayout.clearAnimation();

        imageViewSplash.startAnimation(rotate);

        SplashThread = new Thread(){
            @Override
            public void run() {
                super.run();
                int waited = 0;
                while (waited < 3500) {
                    try {
                        sleep(160);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    waited += 100;
                }

                //intent to next activity
                startActivity(new Intent(Splash.this, MainActivity.class));

                Splash.this.finish();
            }
        };
        SplashThread.start();
    }

}
