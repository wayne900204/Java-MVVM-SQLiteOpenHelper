package com.example.sqlmvvm;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlmvvm.models.UserModel;
import com.example.sqlmvvm.view_models.MainViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Context context;
    private List<UserModel> myListData;
    private MainViewModel mainViewModel;

    public MyAdapter(Context context, MainViewModel mainViewModel) {
        this.context = context;
        this.mainViewModel = mainViewModel;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int i) {
        holder.bindView(myListData.get(i));
    }

    @Override
    public int getItemCount() {
        if (myListData != null)
            return myListData.size();
        else return 0;
    }

    public void setData(List<UserModel> data) {
        this.myListData = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView photo, Name, Time;
        ImageButton delete, edit;

        public ViewHolder(View inflate) {
            super(inflate);
            photo = inflate.findViewById(R.id.photo);
            Name = inflate.findViewById(R.id.Name);
            Time = inflate.findViewById(R.id.Time);
            delete = inflate.findViewById(R.id.delete);
            edit = inflate.findViewById(R.id.edit);
        }

        public void bindView(UserModel userModel) {
            String FirstName = userModel.getFirstNanme();
            String LastName = userModel.getLastName();
            Name.setText(FirstName + LastName);
            photo.setText(makeAbbreviation(FirstName, LastName));
            Time.setText(userModel.getTime());

            View.OnClickListener onDeleteButtonListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainViewModel.deleteData(userModel.getID());
                    notifyDataSetChanged();
                }
            };

            View.OnClickListener onEditButtonListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showEditDialog(getAdapterPosition(), userModel);
                }
            };
            delete.setOnClickListener(onDeleteButtonListener);
            edit.setOnClickListener(onEditButtonListener);
        }
    }

    private String getLocalTime() {
        long time = System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = new Date(time);
        String t1 = format.format(d1);
        Log.e("msg", t1);
        return t1;
    }

    private String makeAbbreviation(String firstName, String lastName) {
        String photoText = "";
        photoText += firstName.substring(0, 1);
        photoText += ".";
        photoText += lastName.substring(0, 1);
        return photoText;
    }

    private void showEditDialog(int i, UserModel dataModel) {
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
                    UserModel userModel = new UserModel(dataModel.getID()
                            , firstName.getText().toString()
                            , lastName.getText().toString()
                            , getLocalTime());
                    mainViewModel.updateData(userModel);
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
