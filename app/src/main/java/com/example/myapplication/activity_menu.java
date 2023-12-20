package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.myapplication.Adapters.MenuAdapter;
import com.example.myapplication.Database.database;
import com.example.myapplication.Classes.*;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class activity_menu extends AppCompatActivity implements RecyclerViewInterface {
    private RecyclerView recyclerView;
    View rootView;
    ArrayList<Dishes> dishes = new ArrayList<Dishes>();
    ArrayList<Dishes> filteredDishes;  // Moved to class-level variable
    Button btn;
    SeekBar seekBar;
    MenuAdapter menuAdapter;
    RatingBar ratingBar;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        recyclerView = findViewById(R.id.MenuView);
        ratingBar=findViewById(R.id.ratingBar);


        Intent intent = getIntent();
        userId= intent.getIntExtra("UserId",-1);
        String restaurantName = intent.getStringExtra("restaurantName");
        int restaurantImageResourceId = intent.getIntExtra("restaurantImage", 0);
        Restaurants restaurant = database.searchRestaurant(restaurantName);
        ratingBar.setRating(restaurant.getRate());
        btn = findViewById(R.id.to_filter);
        btn.setOnClickListener(v -> openFilterDialog());

        filteredDishes = getDishesForRestaurant(restaurantName);

        TextView nameTextView = findViewById(R.id.textView3);
        nameTextView.setText(restaurantName);

        ImageView restaurantImage = findViewById(R.id.imageViewMenu);
        restaurantImage.setImageResource(restaurantImageResourceId);

        menuAdapter = new MenuAdapter(this, filteredDishes, restaurantName, restaurantImageResourceId, this);

        recyclerView.setAdapter(menuAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private ArrayList<Dishes> getDishesForRestaurant(String restaurantName) {
        ArrayList<Dishes> filteredDishes = new ArrayList<>();
        for (Dishes dish : database.dishes) {
            if (dish.getRestaurantName().equals(restaurantName)) {
                filteredDishes.add(dish);
            }
        }
        return filteredDishes;
    }

    private void openFilterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Custom Dialog");

        // Inflate custom layout for the dialog
        View dialogView = getLayoutInflater().inflate(R.layout.filter_dishes, null);
        builder.setView(dialogView);
        RadioGroup radioGroup1 = dialogView.findViewById(R.id.radioGroupMeal);
        RadioGroup radioGroup2 = dialogView.findViewById(R.id.radioGroupCuisine);
        seekBar = dialogView.findViewById(R.id.seekbar_price);
        TextView currentPriceTextView = dialogView.findViewById(R.id.text_current_price);

        Button applyButton = dialogView.findViewById(R.id.button_apply);
        Button resetButton = dialogView.findViewById(R.id.button_reset);
        resetButton.setOnClickListener(view -> {
            radioGroup2.clearCheck();
            radioGroup1.clearCheck();
            seekBar.setProgress(0);
        });

        final int minValue = 30;
        final int maxValue = 500;
        final int seekBarMax = maxValue;

        seekBar.setMax(seekBarMax);
        seekBar.setProgress(minValue);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int currentValue = progress;
                currentPriceTextView.setText("Current Price: $" + currentValue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        applyButton.setOnClickListener(view -> {
            int selectedRadioButtonId1 = radioGroup1.getCheckedRadioButtonId();
            int selectedRadioButtonId2 = radioGroup2.getCheckedRadioButtonId();

            String selectedRadioButton1Name = "";
            String selectedRadioButton2Name = "";

            if (selectedRadioButtonId1 != -1) {
                selectedRadioButton1Name = getResources().getResourceEntryName(selectedRadioButtonId1);
            }
            if (selectedRadioButtonId2 != -1) {
                selectedRadioButton2Name = getResources().getResourceEntryName(selectedRadioButtonId2);
            }

            int seekBarValue = seekBar.getProgress();

            ArrayList<Dishes> updatedFilteredDishes = new ArrayList<>();
            for (Dishes dish : filteredDishes) {
                boolean meetsCriteria = true;

                if (!selectedRadioButton1Name.isEmpty() && !selectedRadioButton1Name.equals(dish.category.name())) {
                    meetsCriteria = false;
                }
                if (!selectedRadioButton2Name.isEmpty() && !selectedRadioButton2Name.equals(dish.cuisineType.name())) {
                    meetsCriteria = false;
                }
                if (dish.getInitPrice() > seekBarValue) {
                    meetsCriteria = false;
                }

                if (meetsCriteria) {
                    updatedFilteredDishes.add(dish);
                }
            }

            menuAdapter.updateData(updatedFilteredDishes);

            AlertDialog dialog = (AlertDialog) view.getTag();
            if (dialog != null) {
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        applyButton.setTag(dialog);
    }

    @Override
    public void onClick(int pos) {
        if (LoginHandler.isLoggedIn()) {
            Intent intent = new Intent(activity_menu.this, activity_customize.class);
            ArrayList<Dishes> f = filteredDishes;
            for (Dishes d: database.dishes){
                if (f.get(pos).getRestaurantName().equals(d.getRestaurantName()) && f.get(pos).getName().equals(d.getName())) {
                    pos = database.dishes.indexOf(d);
                }
            }
            intent.putExtra("name", database.dishes.get(pos).getName());
            intent.putExtra("description", database.dishes.get(pos).getDescription());
            intent.putExtra("index", pos);
            intent.putExtra("UserId", userId);

            startActivity(intent);
        } else {
            Intent intent = new Intent(activity_menu.this, login_page.class);
            startActivity(intent);
        }
    }

    @Override
    public void onlongclick(int pos) {

    }
}
