package com.brian.tienda;
import com.example.tienda.R;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
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


}