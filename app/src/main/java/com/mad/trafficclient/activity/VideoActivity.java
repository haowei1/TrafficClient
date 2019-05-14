package com.mad.trafficclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;

import com.mad.trafficclient.R;

public class VideoActivity extends Activity {

    private static final String TAG = "VideoActivity";
    private String uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        VideoView vv = findViewById(R.id.vv);

        //设置有进度条可以拖动快进
//        MediaController localMediaController = new MediaController(this);
//        vv.setMediaController(localMediaController);
        Intent intent = getIntent();
        int i = (intent.getIntExtra("id", 0)) + 1;
        Log.e(TAG, "onCreate: " + i);
        switch (i) {
            case 1:
                uri = ("android.resource://" + getPackageName() + "/" + R.raw.car1);
                vv.setVideoURI(Uri.parse(uri));
                break;
            case 2:
                uri = ("android.resource://" + getPackageName() + "/" + R.raw.car2);
                vv.setVideoURI(Uri.parse(uri));
                break;
            case 3:
                uri = ("android.resource://" + getPackageName() + "/" + R.raw.car3);
                vv.setVideoURI(Uri.parse(uri));
                break;
            case 4:
                uri = ("android.resource://" + getPackageName() + "/" + R.raw.car4);
                vv.setVideoURI(Uri.parse(uri));
                break;
        }

        vv.start();
    }
}
