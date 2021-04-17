package com.example.sqlmvvm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sqlmvvm.models.UserModel;
import com.example.sqlmvvm.view_models.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageButton addUser;
    private MyAdapter myAdapter;
    private MainViewModel mainViewModel;
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialById();
        setAppBar();

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        settingRecycleView();
        mainViewModel.getAllData().observe(this, new Observer<List<UserModel>>() {
            @Override
            public void onChanged(@Nullable final List<UserModel> data) {
                myAdapter.setData(data);
            }
        });
    }

    private void initialById() {
        recyclerView = findViewById(R.id.recycler);
        addUser = findViewById(R.id.btn_AddUser);
    }

    // BuildToolBar
    private void setAppBar() {
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _showDialog();
            }
        });
    }

    private void settingRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        myAdapter = new MyAdapter(MainActivity.this, mainViewModel);
        recyclerView.setAdapter(myAdapter);
    }

    private void _showDialog() {
        final EditText firstName, lastName;
        final Button btnAdd;
        final Dialog dialog = new Dialog(MainActivity.this);
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
                    UserModel userModel = new UserModel(
                            firstName.getText().toString(),
                            lastName.getText().toString(),
                            mainViewModel.getTime1());
                    mainViewModel.insert(userModel);
                    dialog.dismiss();
                } else {
                    Toast toast = Toast.makeText(MainActivity.this, "此處不能為空白", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER | Gravity.TOP, 0, 0);
                    toast.show();
                }
            }
        };

        btnAdd.setOnClickListener(onButtonListener);
        dialog.show();
    }

}