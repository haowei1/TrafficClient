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
import com.mad.trafficclient.activity.ImageActivity;
import com.mad.trafficclient.activity.VideoActivity;

public class CarBrakeImageFragment extends Fragment {

    private int[] image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_carbreak_viode, null);
        initList();
        GridView gv = view.findViewById(R.id.gv);
        gv.setAdapter(new MyAdapter());

        return view;
    }

    private void initList() {
        image = new int[]{R.drawable.weizhang01, R.drawable.weizhang02,R.drawable.weizhang03,R.drawable.weizhang04};
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return image.length;
        }

        @Override
        public Object getItem(int position) {
            return image[position];
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
            viewHolder.tv.setVisibility(View.INVISIBLE);
            viewHolder.iv.setImageResource(image[position]);
            viewHolder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showImage(position);
                }
            });
            return view;
        }
    }

    private void showImage(int position) {
        Intent intent = new Intent(getActivity(), ImageActivity.class);
        intent.putExtra("id", position+"");
        startActivity(intent);
    }

    class ViewHolder {
        ImageView iv;
        TextView tv;
    }
}
