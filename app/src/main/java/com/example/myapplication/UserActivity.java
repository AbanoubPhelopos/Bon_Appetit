package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import com.example.myapplication.Classes.Dishes;
import com.example.myapplication.Classes.LoginHandler;
import com.example.myapplication.Classes.Restaurants;
import com.example.myapplication.Database.database;
import com.example.myapplication.databinding.ActivityUserBinding;
import com.example.myapplication.fileParsers.adminRead;
import com.example.myapplication.fileParsers.usersWrite;
import com.example.myapplication.fileParsers.usersRead;

import java.util.ArrayList;
import java.util.Random;

public class UserActivity extends AppCompatActivity {

    ArrayList<Integer> dishesId = new ArrayList<>();
    Random random = new Random();

    Bundle extras;

    int userId;

    private ActivityUserBinding binding;

    @Override
    protected void onResume() {
        super.onResume();
        usersRead.parser(this);
        replaceFragment(new MenuFragment(), userId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        adminRead.parser(this);

        if (LoginHandler.isAdmin()) {
            Intent adminDashboardIntent = new Intent(UserActivity.this, AdminActivity.class);
            startActivity(adminDashboardIntent);
            finish();
        }

        extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getInt("UserId");
        }
        replaceFragment(new MenuFragment(), userId);
        binding.bottomNavigationView.setBackground(null);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.ResMenu) {
                replaceFragment(new MenuFragment(), userId);
                return true;
            } else if (itemId == R.id.ResSearch) {
                replaceFragment(new SearchFragment(), userId);
                return true;
            } else if (itemId == R.id.ResProfile) {
                if (LoginHandler.isLoggedIn()) {
                    replaceFragment(new ProfileFragment(), userId);
                    return true;
                } else {
                    Intent intent = new Intent(UserActivity.this, login_page.class);
                    startActivity(intent);
                    return true;
                }
            } else {
                return false;
            }
        });
    }


    private void replaceFragment(Fragment fragment, int id) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putInt("UserId", id);
        fragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void hideMenuItem(int itemId) {
        Menu menu = binding.bottomNavigationView.getMenu();
        MenuItem item = menu.findItem(itemId);
        if (item != null) {
            item.setVisible(false);
        }
    }




}
