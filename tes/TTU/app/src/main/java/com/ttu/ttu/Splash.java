package com.ttu.ttu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ttu.ttu.menu.Menu;

public class Splash extends Activity {

    private final int SPLASH_DISPLAY_LENGHT = 1000; //延遲1秒
    private Activity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        instance = this;

        //Intent intent = new Intent(instance, NotificationService.class);
        //instance.startService(intent);


        new Handler().postDelayed(new Runnable(){

            public void run() {
                Intent intent = new Intent(Splash.this, Menu.class);
                Splash.this.startActivity(intent);
                Splash.this.finish();

            }

        }, SPLASH_DISPLAY_LENGHT);
    }
}
