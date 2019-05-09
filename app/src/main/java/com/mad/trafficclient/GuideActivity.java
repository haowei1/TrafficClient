package com.mad.trafficclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.mad.trafficclient.login.LoginActivity;
import com.mad.trafficclient.util.SpUtils;

public class GuideActivity extends Activity {

	RelativeLayout guide_RL;

	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			startActivity(new Intent(getApplicationContext(), LoginActivity.class));
			finish();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_guide);

		if (SpUtils.getBoolean(getApplicationContext(), "isFirst", false)) {
			startActivity(new Intent(getApplicationContext(), LoginActivity.class));
			finish();
		} else {
            handler.sendEmptyMessageDelayed(0,3000);
		}

		SpUtils.setBoolean(getApplicationContext(), "isFirst", true);

        guide_RL = findViewById(R.id.guide_RL);
        guide_RL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuideActivity.this,
                        LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
	}

}
