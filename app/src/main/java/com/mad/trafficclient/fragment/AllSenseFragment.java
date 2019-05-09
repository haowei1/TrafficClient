package com.mad.trafficclient.fragment;

import com.google.gson.Gson;
import com.mad.trafficclient.R;
import com.mad.trafficclient.entity.AllSense;
import com.mad.trafficclient.util.OkHttpUtils;
import com.mad.trafficclient.util.SpUtils;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class AllSenseFragment extends Fragment {

	private GridView gv;
	private String path = "GetAllSense";
	private static final String TAG = "AllSenseFragment";
	private List<Integer> list = new ArrayList<>();
	private List<String> key;
	private List<Integer> yu;
	private Timer timer;

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
			gv.setAdapter(new MyAdapter());
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.fragment_allsense, container, false);
		gv = view.findViewById(R.id.gv);

		initTimer();

		return view;
	}

	private void initTimer() {
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				list.clear();
				initData();
			}
		}, 0, 3000);
	}

	private void initData() {
		key = new ArrayList<>();
		key.add("温度");
		key.add("湿度");
		key.add("光照");
		key.add("CO2");
		key.add("PM2.5");
		key.add("道路状态");

		yu = getSp();

		Map<String, String> map = new HashMap<>();
		OkHttpUtils.getDataFromIntent(path, map, new OkHttpUtils.CallBack() {
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

	private AllSense parserJson(String result) {
		return new Gson().fromJson(result, AllSense.class);
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Integer getItem(int position) {
			return list.size();
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			ViewHolder viewHolder;
			if (convertView == null) {
				view = View.inflate(getActivity(), R.layout.allsense_item, null);
				viewHolder = new ViewHolder();
				viewHolder.key = view.findViewById(R.id.key);
				viewHolder.value = view.findViewById(R.id.value);
				view.setTag(viewHolder);
			} else {
				view = convertView;
				viewHolder = (ViewHolder) view.getTag();
			}
			if (key != null) {
				viewHolder.key.setText(key.get(position));
			}
			if (list != null) {
				viewHolder.value.setText(list.get(position)+"");

				if (list.get(position) > yu.get(position)) {
					view.setBackgroundColor(Color.RED);
				}else {
					view.setBackgroundColor(Color.GREEN);
				}
			}
			return view;
		}
	}

	class ViewHolder{
		TextView key, value;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		timer.cancel();
	}

	private List<Integer> getSp() {
		yu = new ArrayList<>();
		String wd = SpUtils.getString(getActivity(), "wd", "50");
		String sd = SpUtils.getString(getActivity(), "sd", "50");
		String gz = SpUtils.getString(getActivity(), "gz", "50");
		String co = SpUtils.getString(getActivity(), "co", "50");
		String pm = SpUtils.getString(getActivity(), "pm", "50");
		String dl = SpUtils.getString(getActivity(), "dl", "50");

		yu.add(Integer.parseInt(wd));
		yu.add(Integer.parseInt(sd));
		yu.add(Integer.parseInt(gz));
		yu.add(Integer.parseInt(co));
		yu.add(Integer.parseInt(pm));
		yu.add(Integer.parseInt(dl));
		Log.e(TAG, "getSp: " + yu.size());
		return yu;
	}
}
