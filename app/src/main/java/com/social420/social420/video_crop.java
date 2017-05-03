package com.social420.social420;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

public class video_crop extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_crop);



        VideoView videoView = (VideoView) findViewById(R.id.videopreview);
        Bundle extras = getIntent().getExtras();
        Uri myUri=  Uri.parse(extras.getString("videoUri"));
        videoView.setVideoURI(myUri);



    }
}
