package com.mad.trafficclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mad.trafficclient.R;
import com.mad.trafficclient.activity.VideoActivity;

public class CarBrakeVideoFragment extends Fragment {

    private int[] video;
    private String[] name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_carbreak_viode, null);

        initList();

        GridView gv = view.findViewById(R.id.gv);

        gv.setAdapter(new MyAdapter());

        return view;
    }

    private void initList() {
        video = new int[]{R.raw.car1, R.raw.car2, R.raw.car3, R.raw.car4};
        name = new String[]{"违章视频1", "违章视频2", "违章视频3", "违章视频4"};
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return name.length;
        }

        @Override
        public Object getItem(int position) {
            return name[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View view;
            ViewHolder viewHolder;
            if (convertView == null) {
                view = View.inflate(getActivity(), R.layout.voide_item, null);
                viewHolder = new ViewHolder();
                viewHolder.iv = view.findViewById(R.id.vv);
                viewHolder.tv = view.findViewById(R.id.tv);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }

            viewHolder.tv.setText(name[position]);
            viewHolder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showVideo(position);
                }
            });
            return view;
        }
    }

    private void showVideo(int position) {
        Intent intent = new Intent(getActivity(), VideoActivity.class);
        intent.putExtra("id", position+"");
        startActivity(intent);
    }

    class ViewHolder {
        ImageView iv;
        TextView tv;
    }
}

