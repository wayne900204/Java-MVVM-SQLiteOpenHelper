package com.example.sqlmvvm.repositories;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.sqlmvvm.models.User;
import com.example.sqlmvvm.dbs.UserDBHelper;

import java.util.List;

public class DataBaseRepository {

    private static final String TAG = "Repository";
    Context context;
    UserDBHelper userDBHelper;
    private List<User> myLiveData;

    public DataBaseRepository(Context context) {
        this.context = context;
        userDBHelper = new UserDBHelper(context);
        myLiveData = userDBHelper.getAllData();
    }

    public List<User> getAllData() {
        myLiveData = userDBHelper.getAllData();
        return myLiveData;
    }

    public boolean insertData(User user) {
        try {
            userDBHelper.InsertData(user);
            return true;
        } catch (Exception e) {
            Log.d(TAG, "insertData: " + e.toString());
            return false;
        }
    }

    public boolean deleteData(String id) {
        try {
            userDBHelper.deleteData(id);
            return true;
        } catch (Exception e) {
            Log.d(TAG, "insertData: " + e.toString());
            return false;
        }
    }

    public boolean updateData(User user)  {
        try {
            userDBHelper.updateData(user);
            return true;
        } catch (Exception e) {
            Log.d(TAG, "updateData: " + e.toString());
            return false;
        }
    }

}
