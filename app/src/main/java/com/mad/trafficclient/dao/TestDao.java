package com.mad.trafficclient.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TestDao {

    private static final String TAG = "TestDao";

    private MyDatabaseHelper openHelper;
    //私有化构造方法
    private TestDao(Context context) {
        openHelper = new MyDatabaseHelper(context);
    }
    //声明一个当前类的对象
    private static TestDao testDao = null;
    //提供一个静态方法 如果当前类的对象为空 创建一个新的
    public static TestDao getInstance(Context context) {
        if (testDao == null) {
            testDao = new TestDao(context);
        }
        return testDao;
    }

    /**
     * 测试新增
     */
    public long insert(String username, String password) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        long l = db.insert("test", null, values);
        db.close();
        return l;
    }

    /**
     * 测试删除
     */
    public int delete(int id) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        int i = db.delete("test", "id=?", new String[]{id + ""});
        db.close();
        return i;
    }

    /**
     * 测试更新
     */
    public int update(int id, String username, String password) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", password);
        int i = db.update("test", values, "id=?", new String[]{id + ""});
        db.close();
        return i;
    }

    /**
     * 测试查询所有
     */
    public List<Object> selectAll() {
        ArrayList<Object> list = new ArrayList<>();
        SQLiteDatabase db = openHelper.getWritableDatabase();
        Cursor cursor = db.query("test", null, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String username = cursor.getString(1);
                String password = cursor.getString(2);
                list.add(id);
                list.add(username);
                list.add(password);
            }
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * 根据id进行查询
     * @param id
     * @return
     */
    public List<Object> selectById(int id) {
        List<Object> list = new ArrayList<>();
        SQLiteDatabase db = openHelper.getWritableDatabase();
        Cursor cursor = db.query("test", new String[]{"id", "username", "password"}, "id=?", new String[]{id + ""}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id1 = cursor.getInt(0);
                String username = cursor.getString(1);
                String password = cursor.getString(2);
                list.add(id1);
                list.add(username);
                list.add(password);
            }
        }
        cursor.close();
        db.close();
        return list;
    }

}
