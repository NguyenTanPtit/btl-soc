package com.example.bookstore.activities;

import static com.example.bookstore.R.id.bottom_home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.bookstore.R;
import com.example.bookstore.fragment.CartFragment;
import com.example.bookstore.fragment.DicoverFragment;
import com.example.bookstore.fragment.HomeFragment;
import com.example.bookstore.fragment.ProfileFragment;
import com.example.bookstore.fragment.WishListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        replaceFragment(new HomeFragment());
        customBottomNavigationView();
    }
    private void init(){
        bottomNavigation = findViewById(R.id.bottomNavigation);
    }
    private void customBottomNavigationView() {
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int items = item.getItemId();

                int itemId = item.getItemId();
                if (itemId == bottom_home) {
                    replaceFragment(new HomeFragment());
                    bottomNavigation.getMenu().findItem(bottom_home).setCheckable(true);
                    return true;
                } else if (itemId == R.id.bottom_discover) {
                    replaceFragment(new DicoverFragment());
                    bottomNavigation.getMenu().findItem(R.id.bottom_discover).setChecked(true);
                } else if (itemId == R.id.bottom_cart) {
                    replaceFragment(new CartFragment());
                    bottomNavigation.getMenu().findItem(R.id.bottom_cart).setChecked(true);
                } else if (itemId == R.id.bottom_profile) {
                    replaceFragment(new ProfileFragment());
                    bottomNavigation.getMenu().findItem(R.id.bottom_profile).setChecked(true);
                }
                return false;
            }
        });

    }
    private void replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,fragment,Fragment.class.getName()).commit();
    }
}