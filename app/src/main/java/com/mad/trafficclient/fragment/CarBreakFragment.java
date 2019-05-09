package com.mad.trafficclient.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mad.trafficclient.R;

import java.util.ArrayList;

public class CarBreakFragment extends Fragment {

    private ViewPager viewPager;
    private TextView[] arr_titles;
    private ArrayList<Fragment> mList;
    private LinearLayout layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_carbreak, null);

        layout = view.findViewById(R.id.title);
        viewPager = view.findViewById(R.id.viewPage);

        initFragment();
        initTab();

        viewPager.setAdapter(new MyAdapter(getActivity().getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < arr_titles.length; i++) {
                    arr_titles[i].setEnabled(true);
                    arr_titles[i].setBackgroundColor(Color.WHITE);
                }
                arr_titles[position].setEnabled(false);
                arr_titles[position].setBackgroundColor(Color.GRAY);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        viewPager.setCurrentItem(0);

        return view;
    }

    private void initFragment() {
        mList = new ArrayList<>();
        mList.add(new CarBrakeVideoFragment());
        mList.add(new CarBrakeImageFragment());
    }

    private void initTab() {
        arr_titles = new TextView[2];
        for (int i = 0; i < arr_titles.length; i++) {
            TextView textView = (TextView) layout.getChildAt(i);
            arr_titles[i] = textView;
            arr_titles[i].setBackgroundColor(Color.WHITE);
            arr_titles[i].setEnabled(true);
            arr_titles[i].setTag(i);
            arr_titles[i].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem((Integer) v.getTag());
                }
            });
            arr_titles[0].setBackgroundColor(Color.GRAY);
            arr_titles[0].setEnabled(false);
        }
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            return mList.get(arg0);
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }
}
