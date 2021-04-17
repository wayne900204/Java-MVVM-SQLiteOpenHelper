package com.example.sqlmvvm.dbs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sqlmvvm.models.UserModel;

import java.util.ArrayList;
import java.util.List;

public class UserDBHelper extends SQLiteOpenHelper {

    MutableLiveData<List<UserModel>> resultList = new MutableLiveData<List<UserModel>>();
    public static String DATABASE_NAME = "User";//資料整個庫的名稱
    private static final String TAG = "UserSql";
    public static int version = 1;//版本
    public static String UserName = "UserName";//資料庫名稱

    public UserDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // SQL 語法
        String sql = "CREATE TABLE IF NOT EXISTS " + UserName + "(" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "FirstName TEXT,LastName TEXT,Time TEXT);";
        //使他建立
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {//更新版本用的
        // 把資料表刪除並重新建立
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + UserName);
        onCreate(sqLiteDatabase);
    }

    public long InsertData(UserModel userModel) {
        //ContentValues以鍵值對的形式存放資料
        ContentValues cv = new ContentValues();
        cv.put("FirstName", userModel.getFirstNanme()); //這邊的字串要跟AddItem那邊的Key一樣
        cv.put("LastName", userModel.getLastName());
        cv.put("Time", userModel.getTime());
        long date = getWritableDatabase().insert(UserName, null, cv);
        return date;
    }

    public long updateData(UserModel userModel) {//更新資料用的
        Log.d(TAG, "updateData: " + userModel.getFirstNanme() + userModel.getID());
        ContentValues cv = new ContentValues();
        cv.put("FirstName", userModel.getFirstNanme()); //這邊的字串要跟AddItem那邊的Key一樣
        cv.put("LastName", userModel.getLastName());
        cv.put("Time", userModel.getTime());
        long update = getWritableDatabase().update(UserName, cv, "ID=" + userModel.getID(), null);
        return update;
    }

    public boolean deleteData(String id) {
        long num = getWritableDatabase().delete(UserName, "id='" + id + "'", null);
        //注意這邊字串裡面會有 ‘  '這個'要記得，所以
        if (num != 0)
            return false;
        else
            return true;
    }

    public LiveData<List<UserModel>> getAllData() {
        Log.d(TAG, "getDataContact2: " + "aa");
        String sql = " SELECT * FROM " + UserName + " ORDER BY ID ASC ";
        //從UserName裡面取資料，並排序，由小到大。
        Cursor c = getWritableDatabase().rawQuery(sql, null);
        ArrayList<UserModel> myList = new ArrayList<>();

        while (c.moveToNext()) {
            try {
                String ID = c.getInt(0) + "";
                String FirstName = c.getString(1);
                String LastName = c.getString(2);
                String Time = c.getString(3);
                UserModel userModel = new UserModel(ID, FirstName, LastName, Time);
                myList.add(userModel);
            } catch (Exception e) {
                Log.d(TAG, "getDataContact2: " + e.toString());
                e.printStackTrace();
            }
        }

        c.close();
        resultList.postValue(myList);
        Log.d(TAG, "getDataContact2: ");
        return resultList;
    }
}