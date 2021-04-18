package com.example.sqlmvvm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlmvvm.models.User;
import com.example.sqlmvvm.view_models.MainViewModel;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Context context;
    private List<User> myListData;
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

    public void setData(List<User> data) {
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

        public void bindView(User user) {
            String FirstName = user.getFirstNanme();
            String LastName = user.getLastName();
            Name.setText(FirstName + LastName);
            photo.setText(makeAbbreviation(FirstName, LastName));
            Time.setText(user.getTime());

            View.OnClickListener onDeleteButtonListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainViewModel.deleteData(user.getID());
                    notifyDataSetChanged();
                }
            };

            View.OnClickListener onEditButtonListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainViewModel.showEditDialog(context,getAdapterPosition(), user);
                }
            };
            delete.setOnClickListener(onDeleteButtonListener);
            edit.setOnClickListener(onEditButtonListener);
        }
    }

    private String makeAbbreviation(String firstName, String lastName) {
        String photoText = "";
        photoText += firstName.substring(0, 1);
        photoText += ".";
        photoText += lastName.substring(0, 1);
        return photoText;
    }


}
