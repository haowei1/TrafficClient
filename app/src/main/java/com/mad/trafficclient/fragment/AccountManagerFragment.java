package com.mad.trafficclient.fragment;

import com.mad.trafficclient.R;
import com.mad.trafficclient.dao.AccountDao;
import com.mad.trafficclient.entity.Account;

import android.os.Bundle;
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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AccountManagerFragment extends Fragment {

    private static final String TAG = "AccountManagerFragment";
    private Spinner spinner;
    private Button btnFind;
    private ListView lv;
    private List<Account> list;
    private int temp = 1;

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.fragment_accountmanager, container, false);
        spinner = view.findViewById(R.id.spinner);
        btnFind = view.findViewById(R.id.btn_find);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                temp = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order();
                lv.setAdapter(new MyAdapter());
            }
        });

        lv = view.findViewById(R.id.lv);
        lv.addHeaderView(View.inflate(getActivity(), R.layout.accountmanager_item, null));
        getDataFromDb();

        return view;
	}

    private void order() {
        if (temp == 1) {
            Collections.sort(list, new Comparator<Account>() {
                @Override
                public int compare(Account o1, Account o2) {
                    if (o1.getId() > o2.getId()) {
                        return 1;
                    } else if(o1.getId() > o2.getId()){
                        return 0;
                    }
                    return -1;
                }
            });
        }

        if (temp == 2) {
            Collections.sort(list, new Comparator<Account>() {
                @Override
                public int compare(Account o1, Account o2) {
                    if (o1.getId() > o2.getId()) {
                        return -1;
                    } else if(o1.getId() > o2.getId()){
                        return 0;
                    }
                    return 1;
                }
            });
        }


    }

    private void getDataFromDb() {
        AccountDao dao = AccountDao.getInstance(getActivity());
        list = dao.selectAll();
        lv.setAdapter(new MyAdapter());
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
                view = View.inflate(getActivity(), R.layout.accountmanager_item, null);
                viewHolder = new ViewHolder();
                viewHolder.id = view.findViewById(R.id.id);
                viewHolder.carId = view.findViewById(R.id.carId);
                viewHolder.money = view.findViewById(R.id.money);
                viewHolder.name = view.findViewById(R.id.name);
                viewHolder.time = view.findViewById(R.id.time);

                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }

            if (list != null) {
                Log.e(TAG, "getView: " + list);
                viewHolder.id.setText(list.get(position).getId()+"");
                viewHolder.carId.setText(list.get(position).getCarId());
                viewHolder.money.setText(list.get(position).getMoney());
                viewHolder.name.setText(list.get(position).getName());
                viewHolder.time.setText(list.get(position).getTime());
            }
            return view;
        }
    }
}
class ViewHolder {
    TextView id, carId, name, money, time;
}