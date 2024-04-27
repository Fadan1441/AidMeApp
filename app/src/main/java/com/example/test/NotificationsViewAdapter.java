package com.example.test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotificationsViewAdapter extends RecyclerView.Adapter<NotificationsViewAdapter.MyViewHolder> {
    private List<Notification> dataList;

    public NotificationsViewAdapter(List<Notification> dataList) {
        this.dataList = dataList;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsViewAdapter.MyViewHolder holder, int position) {
        Notification item = dataList.get(position);
        holder.textView.setText(item.getBody());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    @NonNull
    @Override
    public NotificationsViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_layout, parent, false);
        return new NotificationsViewAdapter.MyViewHolder(view);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
