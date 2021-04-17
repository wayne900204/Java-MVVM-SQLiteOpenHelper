package com.example.sqlmvvm.repositorys;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.sqlmvvm.models.UserModel;
import com.example.sqlmvvm.dbs.UserDBHelper;

import java.util.List;

public class DataBaseRepository {

    private static final String TAG = "Repository";
    Context context;
    UserDBHelper userDBHelper;
    private LiveData<List<UserModel>> myLiveData;

    public DataBaseRepository(Context context) {
        this.context = context;
        userDBHelper = new UserDBHelper(context);
        myLiveData = userDBHelper.getAllData();
    }

    public LiveData<List<UserModel>> getAllData() {
        myLiveData = userDBHelper.getAllData();
        return myLiveData;
    }

    public boolean insertData(UserModel userModel) {
        try {
            userDBHelper.InsertData(userModel);
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

    public boolean updateData(UserModel userModel)  {
        try {
            userDBHelper.updateData(userModel);
            return true;
        } catch (Exception e) {
            Log.d(TAG, "updateData: " + e.toString());
            return false;
        }
    }

}
