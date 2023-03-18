package com.example.fashionhub.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.fashionhub.Fragments.CartFragment;
import com.example.fashionhub.Fragments.FavouriteFragment;
import com.example.fashionhub.Fragments.HomeFragment;
import com.example.fashionhub.Fragments.ProfileFragment;
import com.example.fashionhub.R;
import com.example.fashionhub.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id,new HomeFragment()).commit();

        binding.navigationID.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()){

                    case R.id.homeitem_id:
                        fragment = new HomeFragment();
                        break;
                    case R.id.favouriteitem_id:
                        fragment = new FavouriteFragment();
                        break;
                    case R.id.cartitem_id:
                        fragment = new CartFragment();
                        break;
                    case R.id.profileitem_id:
                        fragment = new ProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id,fragment).commit();
                return true;
            }
        });

    }
}