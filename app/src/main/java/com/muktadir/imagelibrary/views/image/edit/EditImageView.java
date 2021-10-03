package com.muktadir.imagelibrary.views.image.edit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.muktadir.imagelibrary.databinding.FragmentEditImageViewBinding;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class EditImageView extends Fragment {
    private static final String TAG = "";
    private FragmentEditImageViewBinding binding;

    public EditImageView() {
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditImageViewBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}