package com.example.sqlmvvm.view_models;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sqlmvvm.R;
import com.example.sqlmvvm.repositories.DataBaseRepository;
import com.example.sqlmvvm.models.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private DataBaseRepository dataBaseRepository;
    private List<User> myLiveData;
    private String TAG = getClass().getSimpleName();
    MutableLiveData<List<User>> resultList = new MutableLiveData<List<User>>();


    public MainViewModel(@NonNull Application application) {
        super(application);
        dataBaseRepository = new DataBaseRepository(application);
        myLiveData = dataBaseRepository.getAllData();
    }

    public LiveData<List<User>> getAllData() {
        myLiveData = dataBaseRepository.getAllData();
        resultList.postValue(myLiveData);
        return resultList;
    }

    public void insert(User user) {
        dataBaseRepository.insertData(user);
        myLiveData = dataBaseRepository.getAllData();
        resultList.postValue(myLiveData);
    }

    public String getTime() {
        long time = System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        String format1 = format.format(date);
        return format1;
    }

    public void deleteData(String id) {
        dataBaseRepository.deleteData(id);
        myLiveData = dataBaseRepository.getAllData();
        resultList.postValue(myLiveData);
    }

    public void updateData(User user) {
        dataBaseRepository.updateData(user);
        myLiveData = dataBaseRepository.getAllData();
        resultList.postValue(myLiveData);
    }

    public void showAddDialog(Context context) {
        final EditText firstName, lastName;
        final Button btnAdd;
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        firstName = dialog.findViewById(R.id.firstName);
        lastName = dialog.findViewById(R.id.lastName);
        btnAdd = dialog.findViewById(R.id.add);
        dialog.getWindow().setAttributes(lp);

        View.OnClickListener onButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!firstName.getText().toString().isEmpty() && !lastName.getText().toString().isEmpty()) {
                    User user = new User(
                            firstName.getText().toString(),
                            lastName.getText().toString(),
                            getTime());
                    insert(user);
                    dialog.dismiss();
                } else {
                    Toast toast = Toast.makeText(context, "此處不能為空白", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER | Gravity.TOP, 0, 0);
                    toast.show();
                }
            }
        };

        btnAdd.setOnClickListener(onButtonListener);
        dialog.show();
    }

    public void showEditDialog(Context context, int i, User dataModel) {
        final EditText firstName, lastName;
        final TextView dialogTitle;
        final Button btnAdd;
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog);
        dialogTitle = dialog.findViewById(R.id.dialogTitle);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        firstName = dialog.findViewById(R.id.firstName);
        lastName = dialog.findViewById(R.id.lastName);
        btnAdd = dialog.findViewById(R.id.add);
        btnAdd.setText("Edit");
        dialogTitle.setText("Edit");
        firstName.setText(dataModel.getFirstNanme());
        lastName.setText(dataModel.getLastName());
        dialog.getWindow().setAttributes(lp);

        View.OnClickListener onButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!firstName.getText().toString().isEmpty() && !lastName.getText().toString().isEmpty()) {
                    User user = new User(dataModel.getID()
                            , firstName.getText().toString()
                            , lastName.getText().toString()
                            , getTime());
                    updateData(user);
                    dialog.dismiss();
                } else {
                    Toast toast = Toast.makeText(context, "此處不能為空白", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER | Gravity.TOP, 0, 0);
                    toast.show();
                }
            }
        };
        btnAdd.setOnClickListener(onButtonListener);
        dialog.show();
    }
}
