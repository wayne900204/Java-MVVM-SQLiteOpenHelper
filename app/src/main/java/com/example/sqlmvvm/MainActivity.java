package com.example.sqlmvvm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.sqlmvvm.models.User;
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
        mainViewModel.getAllData().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable final List<User> data) {
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
                mainViewModel.showAddDialog(MainActivity.this);
            }
        });
    }

    private void settingRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        myAdapter = new MyAdapter(MainActivity.this, mainViewModel);
        recyclerView.setAdapter(myAdapter);
    }
}
