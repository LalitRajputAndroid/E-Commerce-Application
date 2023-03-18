package com.example.fashionhub.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.fashionhub.R;
import com.example.fashionhub.databinding.ActivityAuthBinding;

public class AuthActivity extends AppCompatActivity {

    ActivityAuthBinding binding ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction().replace(R.id.authFramelayout_ID,new SingupFragment()).commit();
    }
}