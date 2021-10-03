package com.muktadir.imagelibrary.views.image;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.muktadir.imagelibrary.R;
import com.muktadir.imagelibrary.databinding.FragmentImageViewBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageView extends Fragment {
    private static final String TAG = "";
    private FragmentImageViewBinding binding;

    public ImageView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Log.d(TAG, "onCreate: ");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentImageViewBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }
}