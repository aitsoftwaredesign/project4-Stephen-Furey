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
    private List<Chat> myChat;
    private String imgURL;

    FirebaseUser firebaseUser;

    public static final int message_type_left = 0;
    public static final int message_type_right = 1;

    public MessageAdapter(Context context, List<Chat> myChat, String imgURL)
    {
        this.context = context;
        this.myChat = myChat;
        this.imgURL = imgURL;
    }


    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if(viewType == message_type_right)
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Chat chat = myChat.get(position);

        holder.show_message.setText(chat.getMessage());

        if(imgURL.equals("defualt"))
        {
            holder.profilePicture.setImageResource((R.mipmap.ic_launcher));
        }
        else
        {
            Glide.with(context).load(imgURL).into(holder.profilePicture);
        }

        if(position == myChat.size()-1)
        {
            if(chat.isIsseen())
            {
                holder.text_seen.setText("Seen");
            }
            else
            {
                holder.text_seen.setText("Delivered");
            }
        }
    }

    @Override
    public int getItemCount()
    {
        return myChat.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView show_message;
        public ImageView profilePicture;
        public TextView text_seen;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            profilePicture = itemView.findViewById(R.id.profilePicture);
            text_seen = itemView.findViewById(R.id.seenLeft);

        }
    }

    public int getItemViewType(int position)
    {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(myChat.get(position).getSender().equals(firebaseUser.getUid()))
        {
            return message_type_right;
        }
        else
        {
            return message_type_left;
        }
    }
}
