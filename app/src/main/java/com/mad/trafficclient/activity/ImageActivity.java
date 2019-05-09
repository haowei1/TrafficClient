package com.mad.trafficclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.mad.trafficclient.R;

public class ImageActivity extends Activity {

    private static final String TAG = "ImageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        ImageView iv = findViewById(R.id.iv);

        Intent intent = getIntent();
        int i = (intent.getIntExtra("id", 0)) + 1;
        Log.e(TAG, "onCreate: " + i);
        switch (i) {
            case 1:
                iv.setImageResource(R.drawable.weizhang01);
                break;
            case 2:
                iv.setImageResource(R.drawable.weizhang02);
                break;
            case 3:
                iv.setImageResource(R.drawable.weizhang03);
                break;
            case 4:
                iv.setImageResource(R.drawable.weizhang04);
                break;
        }
    }
}
