package com.example.exerciseapp;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;
import android.widget.MediaController;

import androidx.appcompat.app.AppCompatActivity;

// Video by cottonbro: https://www.pexels.com/video/two-men-using-jump-ropes-in-exercising-4761426/
// Free to use non-commercially, no claim of ownership is intended
public class IntroVideoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_layout);
        VideoView videoView = (VideoView) findViewById(R.id.videoView);
        String uriPath = "android.resource://com.example.exerciseapp/" + R.raw.jumprope;
        Uri uri = Uri.parse(uriPath);
        videoView.setVideoURI(uri);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                float videoRatio = mediaPlayer.getVideoWidth() / (float) mediaPlayer.getVideoHeight();
                float screenRatio = videoView.getWidth() / (float) videoView.getHeight();
                float scaleX = videoRatio / screenRatio;
                if (scaleX >= 1f) {
                    videoView.setScaleX(scaleX);
                }
                else {
                    videoView.setScaleY(1f / scaleX);
                }
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Intent intent = new Intent(IntroVideoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        videoView.start();
    }

}
