package com.mad.trafficclient.fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mad.trafficclient.R;
import com.mad.trafficclient.entity.AllSense;
import com.mad.trafficclient.util.OkHttpUtils;
import com.mad.trafficclient.util.SpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.NOTIFICATION_SERVICE;

public class YuZhiFragment extends Fragment {

    private static final String TAG = "YuZhiFragment";
    private Switch sw;
    private TextView tvOC;
    private EditText etWD;
    private EditText etSD;
    private EditText etGZ;
    private EditText etCO;
    private EditText etPM;
    private EditText etDL;
    private LinearLayout ll;
    private EditText[] editTexts;
    private Button btnSave;
    private List<Integer> list = new ArrayList<>();
    private ArrayList<Integer> yu;
    private NotificationManager notificationManager;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            AllSense allsense = parserJson((String) msg.obj);
            switch (msg.what) {
                case 1:
                    list.add(allsense.getPm());
                    list.add(allsense.getTemperature());
                    list.add(allsense.getCo2());
                    list.add(allsense.getHumidity());
                    list.add(allsense.getLightIntensity());
                    break;
                case 2:
                    list.add(allsense.getStatus());
                    break;
            }
            Log.e(TAG, "handleMessage: list" + list);
//            gv.setAdapter(new AllSenseFragment.MyAdapter());
            showNotify();
        }
    };
    private Timer timer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_yu_zhi, null);
        notificationManager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
        tvOC = view.findViewById(R.id.oc); // 开关
        sw = view.findViewById(R.id.sw); // switch

        // edit
        etWD = view.findViewById(R.id.wd);
        etSD = view.findViewById(R.id.sd);
        etGZ = view.findViewById(R.id.gz);
        etCO = view.findViewById(R.id.co);
        etPM = view.findViewById(R.id.pm);
        etDL = view.findViewById(R.id.dl);

        editTexts = new EditText[]{etDL, etPM, etCO, etGZ, etSD, etWD};

        btnSave = view.findViewById(R.id.btn_save);

        setSP();
        getSP();

        etClose();

        initTimer();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSP();
                Toast.makeText(getActivity(),"保存成功",Toast.LENGTH_LONG).show();
            }
        });

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tvOC.setText("开");
                    etClose();
                } else {
                    tvOC.setText("关");
                    etOpen();
                }
            }
        });

        return view;
    }

    private void showNotify() {
        if ((list.size() > 0 || list != null) && (yu.size() > 0 || yu != null)) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) > yu.get(i)) {
                    Notify(i);
                }
            }
        }
    }

    private void Notify(int i) {
        String[] names = new String[]{"温度","湿度","光照","CO2","PM2.5", "道路状态"};
        NotificationCompat.Builder notify = new NotificationCompat.Builder(getActivity());
//        Notification.Builder notify = new Notification.Builder(this);
        notify.setSmallIcon(R.drawable.baoma);
        notify.setContentTitle("你有一条新的通知");
        notify.setContentText(names[i] + "报警,阈值 " + yu.get(i) + ",当前值 " + list.get(i));
        notify.setAutoCancel(true);
        notify.setTicker("新消息！");
        notify.setWhen(System.currentTimeMillis());
        Notification notification = notify.build();
        notificationManager.notify(100, notification);
    }


    private void initTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                list.clear();
                getData();
            }
        }, 100, 10000);
    }

    private void getData() {
        Map<String, String> map = new HashMap<>();
        OkHttpUtils.getDataFromIntent("GetAllSense", map, new OkHttpUtils.CallBack() {
            @Override
            public void successful(String string) {
                Log.e(TAG, "successful: body:" + string);
                HashMap<String, String> map1 = new HashMap<>();
                map1.put("RoadId", "1");
                OkHttpUtils.getDataFromIntent("GetRoadStatus", map1, new OkHttpUtils.CallBack() {
                    @Override
                    public void successful(String string) {
                        Log.e(TAG, "successful: road:" + string);
                        Message msg = new Message();
                        msg.obj = string;
                        msg.what = 2;
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void exception(Exception e) {

                    }
                });
                Message msg = new Message();
                msg.what = 1;
                msg.obj = string;
                handler.sendMessage(msg);
            }

            @Override
            public void exception(Exception e) {
                Log.e(TAG, "exception: e" + e);
            }
        });

    }

    private void etOpen() {
        btnSave.setEnabled(true);
        for (int i = 0; i < editTexts.length; i++){
            editTexts[i].setEnabled(true);
        }
    }

    private void etClose() {
        btnSave.setEnabled(false);
        for (int i = 0; i < editTexts.length; i++){
            editTexts[i].setEnabled(false);
        }
    }

    private void getSP() {
        String wd = SpUtils.getString(getActivity(), "wd", "50");
        String sd = SpUtils.getString(getActivity(), "sd", "50");
        String gz = SpUtils.getString(getActivity(), "gz", "50");
        String co = SpUtils.getString(getActivity(), "co", "50");
        String pm = SpUtils.getString(getActivity(), "pm", "50");
        String dl = SpUtils.getString(getActivity(), "dl", "4");

        Log.e(TAG, "getSP: " + wd + sd + gz + co + pm + dl);

        etWD.setText(wd);
        etSD.setText(sd);
        etGZ.setText(gz);
        etCO.setText(co);
        etPM.setText(pm);
        etDL.setText(dl);

        yu = new ArrayList<>();
        yu.add(Integer.parseInt(wd));
        yu.add(Integer.parseInt(sd));
        yu.add(Integer.parseInt(gz));
        yu.add(Integer.parseInt(co));
        yu.add(Integer.parseInt(pm));
        yu.add(Integer.parseInt(dl));
    }

    private void setSP() {
        String wd = etWD.getText().toString().trim();
        String sd = etSD.getText().toString().trim();
        String gz = etGZ.getText().toString().trim();
        String co = etCO.getText().toString().trim();
        String pm = etPM.getText().toString().trim();
        String dl = etDL.getText().toString().trim();

        if (!TextUtils.isEmpty(wd) && !TextUtils.isEmpty(sd) && !TextUtils.isEmpty(gz) && !TextUtils.isEmpty(co) &&
                !TextUtils.isEmpty(pm) && !TextUtils.isEmpty(dl)) {
            SpUtils.setString(getActivity(), "wd", wd);
            SpUtils.setString(getActivity(), "sd", sd);
            SpUtils.setString(getActivity(), "gz", gz);
            SpUtils.setString(getActivity(), "co", co);
            SpUtils.setString(getActivity(), "pm", pm);
            SpUtils.setString(getActivity(), "dl", dl);
        }
    }

    private AllSense parserJson(String result) {
        return new Gson().fromJson(result, AllSense.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        timer.cancel();
    }
}
