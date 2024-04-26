package com.example.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {


    Context context;
    List<FriendsNamesandID> friends;

    public RecycleViewAdapter(Context context, List<FriendsNamesandID> friends){

        this.context = context;
        this.friends = friends;
    }

    @NonNull
    @Override
    public RecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Giving a look to our rows
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycle_view_row,parent,false);

        return new RecycleViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapter.MyViewHolder holder, int position) {
        //assigning values to each our rows based on the position of our recycler view
        holder.friendName.setText(friends.get(position).getFriendName());
        holder.friendId.setText(friends.get(position).getFriendId());
        //holder.deleteFriend.
    }

    @Override
    public int getItemCount() {
        //recycler wants to know how many items is there
        return friends.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
          //grabbing the views from our recycler_view_row file
        TextView friendName, friendId;
        Button deleteFriend;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
           friendName = itemView.findViewById(R.id.friendName);
           friendId = itemView.findViewById(R.id.friendID);
           deleteFriend = itemView.findViewById(R.id.deleteFriend);


        }
    }

}
