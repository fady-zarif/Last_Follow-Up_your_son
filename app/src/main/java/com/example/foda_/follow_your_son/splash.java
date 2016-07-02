package com.example.foda_.follow_your_son;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread thread = new Thread()
        {
            @Override
            public void run() {
                super.run();
                try{
                    sleep(2500);
                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }
                finally {
                    Intent intent=new Intent(splash.this,MainActivity.class);
                    startActivity(intent);
                    splash.this.finish();
                }
            }
        };
        thread.start();


    }
}
