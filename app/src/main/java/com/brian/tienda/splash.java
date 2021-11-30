package com.brian.tienda;

import com.example.tienda.R;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;

public class splash extends AppCompatActivity {
   VideoView video;
    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                readUserPreferences(getApplicationContext());
            }
        }, 3000);

        video = (VideoView)findViewById(R.id.videoView);
        String patch = new StringBuilder("android.resource://").append(getPackageName()).append("/raw/computec").toString();
        video.setVideoPath(patch);
        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new  Intent(splash.this, getstart.class));
                      finish();
                    }
                },0);
            }
        });

        video.start();
    }
    public void readUserPreferences(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.user_preference_key), Context.MODE_PRIVATE);
        boolean status = sharedPref.getBoolean("status",false);



        if(status){
            Intent intent = new Intent(this, productList.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this, getstart.class);
            startActivity(intent);
        }
    }

    public String rol (Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.user_preference_key), Context.MODE_PRIVATE);
        String rol = sharedPref.getString("rol",null);
        return rol;
    }

}