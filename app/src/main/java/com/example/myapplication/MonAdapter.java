package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Classes.Dishes;
import com.example.myapplication.Classes.Payment;
import com.example.myapplication.Adapters.ResAdapter;
import com.example.myapplication.Classes.user;
import com.example.myapplication.Database.database;

import java.util.ArrayList;
import java.util.Date;

public class MonAdapter extends RecyclerView.Adapter<MonAdapter.MonViewHolder> {
    Context context;
    ArrayList<Payment> payments;
    ArrayList<user> users;
    ArrayList<Dishes> dishes;

    int index;
    public MonAdapter(Context context, ArrayList<Payment> payments, ArrayList<user> users, ArrayList<Dishes> dishes, int index){
        this.context = context;
        this.payments = payments;
        this.users = users;
        this.dishes = dishes;
        this.index = index;
    }


    @NonNull
    @Override
    public MonAdapter.MonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MonAdapter.MonViewHolder(LayoutInflater.from(context).inflate(R.layout.monitor_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MonAdapter.MonViewHolder holder, int position) {
        Payment payment = payments.get(position);

        user currentUser = null;
        Dishes currentDish = null;

        for (user u : users) {
            for (Dishes d : dishes) {
                if (u.getId() == payment.getUserId() && d.getId() == payment.getDishId() &&
                        d.getRestaurantName().equals(database.adminList.get(index).getResturant())) {
                    currentUser = u;
                    currentDish = d;
                    break;
                }
            }
            if (currentUser != null && currentDish != null) {
                break;
            }
        }

        if (currentUser != null && currentDish != null) {
            holder.Name.setText(currentUser.getUserName());
            holder.Dish.setText(currentDish.getName());
            holder.Street.setText(currentUser.getStreet());
            holder.City.setText(currentUser.getCity());
        }
    }


    @Override
    public int getItemCount() {
        return payments.size();
    }

    public static class MonViewHolder extends RecyclerView.ViewHolder{
        TextView Name,City,Street,Dish;
        public MonViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.UserName);
            City = itemView.findViewById(R.id.City);
            Street = itemView.findViewById(R.id.Street);
            Dish = itemView.findViewById(R.id.item);
        }
    }
}
