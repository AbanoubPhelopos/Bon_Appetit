package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Classes.Dishes;
import com.example.myapplication.Adapters.RemoveAdapter;
import com.example.myapplication.Classes.admin;
import com.example.myapplication.Database.database;

import java.util.ArrayList;

public class activity_remove_dish extends AppCompatActivity implements RecyclerViewInterface {

    ArrayList<Dishes> dishes = database.dishes;
    ArrayList <Dishes> resdishes = null;
    RemoveAdapter adapter;
    int index;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remove_dish);

        // Retrieve the index from the intent
        index = getIntent().getIntExtra("index", -1);

        RecyclerView recyclerView= findViewById(R.id.removeRyclerview);

        String resname = database.adminList.get(index).getResturant();


        resdishes = new ArrayList<>();

        for (Dishes d : dishes) {
            if (d.getRestaurantName().equals(resname)) {
                resdishes.add(d);
            }
        }

        adapter = new RemoveAdapter(this, resdishes, resname, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public void onClick(int pos) {



    }

    @Override
    public void onlongclick(int pos) {
        int databasePos = -1;
        for (Dishes d:database.dishes) {
            if (resdishes.get(pos).getName().equals(d.getName()) && resdishes.get(pos).getRestaurantName().equals(d.getRestaurantName())){
                databasePos = dishes.indexOf(d);
                break;
            }


        }
        resdishes.remove(pos);
        database.dishes.remove(databasePos);
        adapter.notifyItemRemoved(pos);

    }


}