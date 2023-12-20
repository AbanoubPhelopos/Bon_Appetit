package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.Classes.LoginHandler;
import com.example.myapplication.databinding.ActivityAdminBinding;
import com.example.myapplication.databinding.ActivityUserBinding;


public class AdminActivity extends AppCompatActivity {

    private ActivityAdminBinding binding;
    int index;

    @Override
    protected void onResume() {
        super.onResume();
        replaceFragment(new AdminEditFragment(), index);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //setContentView(R.layout.activity_admin);
        index = getIntent().getIntExtra("index", -1);

        replaceFragment(new MenuFragment(), index);
        binding.AdminBottomNavigationView.setBackground(null);

        binding.AdminBottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.AdminEditDishes) {
                replaceFragment(new AdminEditFragment(), index);
                return true;
            } else if (itemId == R.id.AdminMonitorOrder) {
                replaceFragment(new AdminMonitorFragment(), index);
                return true;
            } else if (itemId == R.id.AdminProfile) {
                if(LoginHandler.isLoggedIn() ){

                    replaceFragment(new AdminProfileFragment(), index);
                    return true;
                }else{
                    Intent intent = new Intent(AdminActivity.this, login_page.class);
                    startActivity(intent);
                    return true;
                }

            } else {
                return false;
            }
        });

    }
    private void replaceFragment(Fragment fragment, int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        fragment.setArguments(bundle);


        fragmentTransaction.replace(R.id.Admin_fragment_container, fragment);
        fragmentTransaction.commit();
    }

}