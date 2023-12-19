package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Database.database;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Classes.Dishes;

import java.util.ArrayList;

public class activity_customize extends AppCompatActivity {

    ArrayList<Dishes> dishes = database.dishes;


    TextView name, description, count;
    Button increment, decrement, addSpicy, addExtra,Buy;
    int numberOfDishes = 0;
    Bundle extras;

    @SuppressLint({"MissingInflatedId", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);

        // Transfer name and description from menu
        name = findViewById(R.id.nameofdishe);
        description = findViewById(R.id.discritionofdishe);

        extras = getIntent().getExtras();
        int index = extras.getInt("index");
        int userId = extras.getInt("UserId");
        Dishes currentDish=dishes.get(index);
        if (extras != null) {
            name.setText(dishes.get(index).getName());
            description.setText(dishes.get(index).getDescription());
        }

        // Number of dishes
        increment = findViewById(R.id.increment);
        decrement = findViewById(R.id.decrement);
        count = findViewById(R.id.count);

        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfDishes < 10) {
                    numberOfDishes++;
                }
                updateCount();
            }
        });

        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfDishes > 1) {
                    numberOfDishes--;
                }
                updateCount();
            }
        });

        // Add spicy
        addSpicy = findViewById(R.id.spicy);
        addSpicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButtonColor(addSpicy);
                currentDish.setSpicy(!currentDish.isSpicy());
            }
        });

        // Add extra
        addExtra = findViewById(R.id.add);
        addExtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButtonColor(addExtra);
                if (addExtra.getTag() != null && (int) addExtra.getTag() == Color.RED) {
                    currentDish.setExtra(10f);
                } else {
                    currentDish.setExtra(0f);
                }
            }
        });

        // Move to cart
        Buy = findViewById(R.id.BUY);
        Buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dishes.get(index).setNoOfDishes(numberOfDishes);
                BuyButton(index,userId);
            }
        });

    }

    private void updateCount() {
        count.setText(String.valueOf(numberOfDishes));
    }

    private void BuyButton(int index,int uId) {
        Intent intent = new Intent(this, BuyActivity.class);
        intent.putExtra("index", index);
        intent.putExtra("UserId", uId);
        startActivity(intent);
    }

    private void toggleButtonColor(Button button) {
        int currentColor = button.getTag() != null ? (int) button.getTag() : Color.parseColor("#FC6B03");
        int newColor = (currentColor == Color.RED) ? Color.parseColor("#FC6B03") : Color.RED;

        button.setBackgroundColor(newColor);
        button.setTag(newColor);
    }
}
