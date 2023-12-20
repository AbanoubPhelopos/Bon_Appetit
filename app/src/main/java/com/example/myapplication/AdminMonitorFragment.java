package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Classes.Dishes;
import com.example.myapplication.Classes.Payment;
import com.example.myapplication.Adapters.ResAdapter;
import com.example.myapplication.Classes.Restaurants;
import com.example.myapplication.Classes.user;
import com.example.myapplication.Database.database;

import java.util.ArrayList;
import java.util.List;

public class AdminMonitorFragment extends Fragment  {
    private RecyclerView recyclerView;
    private View rootView;
    public int UserIndex;
    public ArrayList<Payment> payments;
    public ArrayList<user> orderedUsers = new ArrayList<>();
    public ArrayList<Dishes> orderedDishes = new ArrayList<>();
    public int index;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        index = getArguments() != null ? getArguments().getInt("index", -1) : -1;
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_admin_monitor, container, false);


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = rootView.findViewById(R.id.MonitorRecyclerView);

        orderedUsers = getUsers();
        orderedDishes = getOrderedDishes();
        payments = database.payments;


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new MonAdapter(getContext(), payments, orderedUsers,orderedDishes, index));

    }

    public ArrayList<Dishes> getOrderedDishes(){
        for(Dishes dish: database.dishes){
            for (Payment pay: database.payments){
                if (pay.getDishId() == dish.getId()){
                    orderedDishes.add(dish);
                }
            }
        }
        return orderedDishes;
    }

    public ArrayList<user> getUsers(){
        for(Payment pay: database.payments){
            for (user users: database.userList){
                if (pay.getUserId() == users.getId() && !orderedUsers.contains(users)){
                    orderedUsers.add(users);
                }
            }
        }
        return orderedUsers;
    }


}

