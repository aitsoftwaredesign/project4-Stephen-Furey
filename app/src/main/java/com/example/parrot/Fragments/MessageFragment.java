package com.example.parrot.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parrot.Adapter.UserAdapter;
import com.example.parrot.Model.ChatList;
import com.example.parrot.Model.Users;
import com.example.parrot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment
{
    private UserAdapter userAdapter;
    private List<Users> myUsers;

    FirebaseUser firebaseUser;
    DatabaseReference myRef;

    private List<ChatList> usersList;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_chat. container, false);

        recyclerView = view.findViewById(R.id.recyclerView.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(new getContext()));

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        userList = new ArrayList<>();

        myRef = FirebaseDatabase.getInstance().getReference("ChatList").child(firebaseUser.getUid());

        myRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                usersList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    ChatList chatList = snapshot.getValue(ChatList.class);
                    usersList.add(chatList);
                }

                chatList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
        return view;
    }

    private void chatList()
    {
        myUsers = new ArrayList<>();
        myRef = FirebaseDatabase.getInstance().getReference("MyUsers");
        myRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                myUsers.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Users user = snapshot.getValue(Users.class);
                    for (ChatList chatlist : usersList)
                    {
                        if(user.getId().equals(chatlist.getId()))
                        {
                            myUsers.add(user);
                        }
                    }
                }

                userAdapter = new UserAdapter(getContext(), myUsers);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }

}