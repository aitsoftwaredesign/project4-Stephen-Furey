package com.example.parrot.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.parrot.MessageActivity;
import com.example.parrot.Model.Users;
import com.example.parrot.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>
{
    private Context context;
    private List<Users> myUsers;
    private boolean state;

    public UserAdapter(Context context, List<Users> myUsers, boolean state)
    {
        this.context = context;
        this.myUsers = myUsers;
        this.state = state;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Users users = myUsers.get(position);
        holder.username.setText(users.getUsername());

        if(users.getImageURL().equals("default"))
        {
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
        }
        else
        {
            Glide.with(context).load(users.getImageURL()).into(holder.imageView);
        }

        if(state)
        {
            if(users.getStatus().equals("Online"))
            {
                holder.imageViewOnline.setVisibility(View.VISIBLE);
                holder.imageViewOffline.setVisibility(View.GONE);
            }
            else
            {
                holder.imageViewOnline.setVisibility(View.GONE);
                holder.imageViewOffline.setVisibility(View.VISIBLE);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(context, MessageActivity.class);
                i.putExtra("userid", users.getId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return myUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView username;
        public ImageView imageView;
        public ImageView imageViewOnline;
        public ImageView imageViewOffline;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            imageView = itemView.findViewById(R.id.profilePicture);
            imageViewOnline = itemView.findViewById(R.id.statusOnline);
            imageViewOffline = itemView.findViewById(R.id.statusOffline);
        }
    }
}
