package com.mad.trafficclient.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mad.trafficclient.entity.Account;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {

    private static final String TAG = "AccountDao";
    private MyDatabaseHelper openHelper;
    //私有化构造方法
    private AccountDao(Context context) {
        openHelper = new MyDatabaseHelper(context);
    }
    //声明一个当前类的对象
    private static AccountDao accountDao = null;
    //提供一个静态方法 如果当前类的对象为空 创建一个新的
    public static AccountDao getInstance(Context context) {
        if (accountDao == null) {
            accountDao = new AccountDao(context);
        }
        return accountDao;
    }

    /**
     * 测试新增
     */
    public long insert(String carId, String money, String name) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("carId", carId);
        values.put("money", money);
        values.put("name", name);
        values.put("time", getTime());
        long l = db.insert("account", null, values);
        db.close();
        return l;
    }

    /**
     * 获取时间
     */
    public String getTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = format.format(System.currentTimeMillis());
        Log.e(TAG, "getTime: " + time);
        return time;
    }

    /**
     * 查询全部
     */
    public List<Account> selectAll() {
        ArrayList<Account> list = new ArrayList<>();
        SQLiteDatabase db = openHelper.getWritableDatabase();
        Cursor cursor = db.query("account", null, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Account account = new Account();
                int id = cursor.getInt(0);
                String carId = cursor.getString(1);
                String money = cursor.getString(2);
                String name = cursor.getString(3);
                String time = cursor.getString(4);
                account.setId(id);
                account.setCarId(carId);
                account.setMoney(money);
                account.setName(name);
                account.setTime(time);
                list.add(account);
            }
        }
        cursor.close();
        db.close();
        return list;
    }
}
