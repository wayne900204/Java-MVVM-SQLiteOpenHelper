package com.example.sqlmvvm.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sqlmvvm.repositorys.DataBaseRepository;
import com.example.sqlmvvm.models.UserModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private DataBaseRepository dataBaseRepository;
    private LiveData<List<UserModel>> myLiveData;
    private String TAG = getClass().getSimpleName();

    public MainViewModel(@NonNull Application application) {
        super(application);
        dataBaseRepository = new DataBaseRepository(application);
        myLiveData = dataBaseRepository.getAllData();
    }

    public LiveData<List<UserModel>> getAllData() {
        myLiveData = dataBaseRepository.getAllData();
        return myLiveData;
    }

    public void insert(UserModel userModel) {
        Boolean success = false;
        success = dataBaseRepository.insertData(userModel);
        myLiveData = dataBaseRepository.getAllData();
        if (success) { } else { }
    }

    public String getTime1() {
        long time = System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = new Date(time);
        String t1 = format.format(d1);
        return t1;
    }

    public void deleteData(String id) {
        Boolean success = false;
        success = dataBaseRepository.deleteData(id);
        myLiveData = dataBaseRepository.getAllData();
        if (success) { } else { }
    }

    public void updateData(UserModel userModel) {
        Boolean success = false;
        success = dataBaseRepository.updateData(userModel);
        myLiveData = dataBaseRepository.getAllData();
        if (success) { } else { }
    }
}
