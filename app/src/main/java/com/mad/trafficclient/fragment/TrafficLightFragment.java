/**
 * 
 */
package com.mad.trafficclient.fragment;

import com.google.gson.Gson;
import com.mad.trafficclient.R;
import com.mad.trafficclient.entity.Traffic;
import com.mad.trafficclient.util.OkHttpUtils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrafficLightFragment extends Fragment {

	private static final String TAG = "TrafficLightFragment";
	private String path = "GetTrafficLightConfigAction";
	private Spinner spinner;
	private Button btn;
	private ListView lv;
	private int temp = 0;
	private List<Traffic> list = new ArrayList<>();

	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 1:
					String result1 = (String) msg.obj;
					list.add(parser(result1,1));
					break;
				case 2:
					String result2 = (String) msg.obj;
					list.add(parser(result2,2));
					break;
				case 3:
					String result3 = (String) msg.obj;
					list.add(parser(result3,3));
					break;
				case 0:
					lv.setAdapter(new MyAdapter());
					break;
			}
			lv.setAdapter(new MyAdapter());
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_trafficlight, container, false);

		spinner = view.findViewById(R.id.spinner);
		btn = view.findViewById(R.id.btn);
		lv = view.findViewById(R.id.lv);
		lv.addHeaderView(View.inflate(getActivity(), R.layout.item_traffic_hader, null));

		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				temp = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				order(temp);
			}
		});

		initData();

		return view;
	}

	private synchronized void initData() {
		Map<String, String> map1 = new HashMap<>();
		map1.put("TrafficLightId", "1");
		OkHttpUtils.getDataFromIntent(path, map1, new OkHttpUtils.CallBack() {
			@Override
			public void successful(String string) {
				Log.e(TAG, "successful: body1" + string);
				Message msg = new Message();
				msg.obj = string;
				msg.what = 1;
				handler.sendMessage(msg);
				Map<String, String> map2 = new HashMap<>();
				map2.put("TrafficLightId", "2");
				OkHttpUtils.getDataFromIntent(path, map2, new OkHttpUtils.CallBack() {
					@Override
					public void successful(String string) {
						Log.e(TAG, "successful: body2" + string);
						Message msg = new Message();
						msg.obj = string;
						msg.what = 2;
						handler.sendMessage(msg);
						Map<String, String> map3 = new HashMap<>();
						map3.put("TrafficLightId", "3");
						OkHttpUtils.getDataFromIntent(path, map3, new OkHttpUtils.CallBack() {
							@Override
							public void successful(String string) {
								Log.e(TAG, "successful: body3" + string);
								Message msg = new Message();
								msg.obj = string;
								msg.what = 3;
								handler.sendMessage(msg);
							}
							@Override
							public void exception(Exception e) {
								Log.e(TAG, "exception: " + e);
							}
						});
					}

					@Override
					public void exception(Exception e) {
						Log.e(TAG, "exception: " + e);
					}
				});
			}
			@Override
			public void exception(Exception e) {
				Log.e(TAG, "exception: " + e);
			}
		});

	}

	private Traffic parser(String string,int temp) {
		Gson gson = new Gson();
		Traffic traffic = gson.fromJson(string, Traffic.class);
		traffic.setId(temp);
		return traffic;
	}


	private void order(int temp) {
		Log.e(TAG, "order: oldList" + list);

		if (temp == 0) {
			Collections.sort(list, new Comparator<Traffic>() {
				@Override
				public int compare(Traffic o1, Traffic o2) {
					if (o1.getId() > o2.getId()){
						return 1;
					}
					if (o1.getId() == o2.getId()){
						return 0;
					}
					return -1;
				}
			});
		}
		if (temp == 1) {
			Collections.sort(list, new Comparator<Traffic>() {
				@Override
				public int compare(Traffic o1, Traffic o2) {
					if (o1.getId() < o2.getId()){
						return 1;
					}
					if (o1.getId() == o2.getId()){
						return 0;
					}
					return -1;
				}
			});
		}

		if (temp == 2) {
			Collections.sort(list, new Comparator<Traffic>() {
				@Override
				public int compare(Traffic o1, Traffic o2) {
					if (o1.getRedTime() > o2.getRedTime()){
						return 1;
					}
					if (o1.getId() == o2.getId()){
						return 0;
					}
					return -1;
				}
			});
		}
		if (temp == 3) {
			Collections.sort(list, new Comparator<Traffic>() {
				@Override
				public int compare(Traffic o1, Traffic o2) {
					if (o1.getRedTime() < o2.getRedTime()){
						return 1;
					}
					if (o1.getId() == o2.getId()){
						return 0;
					}
					return -1;
				}
			});
		}

		if (temp == 4) {
			Collections.sort(list, new Comparator<Traffic>() {
				@Override
				public int compare(Traffic o1, Traffic o2) {
					if (o1.getGreenTime() > o2.getGreenTime()){
						return 1;
					}
					if (o1.getId() == o2.getId()){
						return 0;
					}
					return -1;
				}
			});
		}
		if (temp == 5) {
			Collections.sort(list, new Comparator<Traffic>() {
				@Override
				public int compare(Traffic o1, Traffic o2) {
					if (o1.getGreenTime() < o2.getGreenTime()){
						return 1;
					}
					if (o1.getId() == o2.getId()){
						return 0;
					}
					return -1;
				}
			});
		}

		if (temp == 6) {
			Collections.sort(list, new Comparator<Traffic>() {
				@Override
				public int compare(Traffic o1, Traffic o2) {
					if (o1.getYellowTime() > o2.getYellowTime()){
						return 1;
					}
					if (o1.getId() == o2.getId()){
						return 0;
					}
					return -1;
				}
			});
		}
		if (temp == 7) {
			Collections.sort(list, new Comparator<Traffic>() {
				@Override
				public int compare(Traffic o1, Traffic o2) {
					if (o1.getYellowTime() < o2.getYellowTime()){
						return 1;
					}
					if (o1.getId() == o2.getId()){
						return 0;
					}
					return -1;
				}
			});
		}

		handler.sendEmptyMessage(0);
		Log.e(TAG, "order: temp:" + temp);

		Log.e(TAG, "order: newList" + list);
		handler.sendEmptyMessage(0);
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
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
				view = View.inflate(getActivity(),R.layout.item_traffic_hader,null);
				viewHolder = new ViewHolder();
				viewHolder.id = view.findViewById(R.id.id);
				viewHolder.red = view.findViewById(R.id.red);
				viewHolder.yellow = view.findViewById(R.id.yellow);
				viewHolder.green = view.findViewById(R.id.green);
				view.setTag(viewHolder);
			} else {
				view = convertView;
				viewHolder = (ViewHolder) view.getTag();
			}
			viewHolder.id.setText(list.get(position).getId()+"");
			viewHolder.red.setText(list.get(position).getRedTime()+"");
			viewHolder.yellow.setText(list.get(position).getYellowTime()+"");
			viewHolder.green.setText(list.get(position).getGreenTime()+"");
			return view;
		}
	}

	class ViewHolder {
		TextView id, red, yellow, green;
	}
}
