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
import com.example.parrot.Model.Chat;
import com.example.parrot.Model.Users;
import com.example.parrot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>
{
    private Context context;
    private List<Chat> chat;
    private String imgURL;

    FirebaseUser firebaseUser;

    public static final int message_type_left = 0;
    public static final int message_type_right = 1;

    public MessageAdapter(Context context, List<Chat> chat, String imgURL)
    {
        this.context = context;
        this.chat = chat;
        this.imgURL = imgURL;
    }


    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if (viewType == message_type_right)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position)
    {
        Chat chat = chat.getMessage(position);

        holder.show_message.setText(chat.getMessage());

        if(imgURL.equals("defualt"))
        {
            holder.profilePicture.setImageResource((R.mipmap.ic_launcher));
        }
        else
        {
            Glide.with(context).load(imgURL).into(holder.profilePicture);
        }
    }

    @Override
    public int getItemCount()
    {
        return chat.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView show_message;
        public ImageView profilePicture;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            profilePicture = itemView.findViewById(R.id.profilePicture);
        }
    }

    public int getItemViewType(int position)
    {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(chat.get(position).getSender().equals(firebaseUser.getUid()))
        {
            return message_type_right;
        }
        else
        {
            return message_type_left;
        }
    }
}
