package com.muktadir.imagelibrary.views;

import android.Manifest;
import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.muktadir.imagelibrary.BaseActivity;
import com.muktadir.imagelibrary.databinding.ActivityMainHostBinding;

public class MainHostActivity extends BaseActivity {
    private ActivityMainHostBinding binding;
    private NavController navController;
    private NavHostFragment navHostController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainHostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navHostController = (NavHostFragment)getSupportFragmentManager().findFragmentById(binding.navHostFragment.getId());
        navController = navHostController.getNavController();
        requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }
}