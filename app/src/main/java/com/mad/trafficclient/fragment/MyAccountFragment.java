/**
 * 
 */
package com.mad.trafficclient.fragment;

import com.mad.trafficclient.R;
import com.mad.trafficclient.dao.AccountDao;
import com.mad.trafficclient.util.OkHttpUtils;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;

public class MyAccountFragment extends Fragment {

    private static final String TAG = "MyAccountFragment";
    private int carId = 1;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String result1 = (String) msg.obj;
                    String s1 = praserJson1(result1);
                    tvYue.setText(s1);
                    break;
                case 2:
                    String result2 = (String) msg.obj;
                    String s2 = praserJson2(result2);
                    if (s2.equals("ok")) {
                        long l = save2db();
                        if (l > 0) {
                            Toast.makeText(getActivity(), "充值成功", Toast.LENGTH_LONG).show();
                            getMoney();
                        }
                    }
                    break;
            }
        }
    };

    private long save2db() {
        AccountDao dao = AccountDao.getInstance(getActivity());
        String money = etMoney.getText().toString().trim();
        long l = dao.insert(carId + "", money, "admin");
        if (l > 0) {
            Log.e(TAG, "save2db: " + "充值成功");
        }
        return l;
    }

    private String praserJson2(String result2) {
        try {
            JSONObject jo = new JSONObject(result2);
            String result = jo.getString("result");
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private TextView tvYue;
    private Spinner spinner;
    private EditText etMoney;
    private Button btnCx;
    private Button btnCz;

    private String praserJson1(String result) {
        try {
            JSONObject jo = new JSONObject(result);
            String balance = jo.getString("Balance");
            return balance;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.fragment_myaccount, container, false);
        tvYue = view.findViewById(R.id.yue);
        spinner = view.findViewById(R.id.carId);
        etMoney = view.findViewById(R.id.money);
        btnCx = view.findViewById(R.id.btn_cx);
        btnCz = view.findViewById(R.id.btn_cz);

        getMoney();

        initCx();

        initCz();


        return view;
	}

    private void initCz() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                carId = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                carId = 1;
            }
        });
        btnCz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMoney();
            }
        });
    }

    private void setMoney() {
        String money = etMoney.getText().toString().trim();
        if (money.length() > 3 || money.length() < 1) {
            Toast.makeText(getActivity(), "输入1~999之间的整数", Toast.LENGTH_LONG).show();
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("CarId", carId+"");
        map.put("Money", money);
        OkHttpUtils.getDataFromIntent("SetCarAccountRecharge", map, new OkHttpUtils.CallBack() {
            @Override
            public void successful(String string) {
                Log.e(TAG, "successful: " + string);
                Message msg = new Message();
                msg.obj = string;
                msg.what = 2;
                handler.sendMessage(msg);
            }

            @Override
            public void exception(Exception e) {

            }
        });
    }

    private void initCx() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                carId = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                carId = 1;
            }
        });

        btnCx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMoney();
            }
        });
    }


    private void getMoney() {
        HashMap<String, String> map = new HashMap<>();
        map.put("CarId", carId+"");
        OkHttpUtils.getDataFromIntent("GetCarAccountBalance", map, new OkHttpUtils.CallBack() {
            @Override
            public void successful(String string) {
                Log.e(TAG, "successful: " + string);
                Message msg = new Message();
                msg.obj = string;
                msg.what = 1;
                handler.sendMessage(msg);
            }

            @Override
            public void exception(Exception e) {

            }
        });
    }

}
