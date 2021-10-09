package com.muktadir.imagelibrary.views.image.photo.core;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.muktadir.imagelibrary.R;
import com.muktadir.imagelibrary.databinding.FragmentBottomPropertiesDialogBinding;
import com.muktadir.imagelibrary.views.image.photo.colors.ColorPickerAdapter;


public class PropertiesFragment extends BottomSheetDialogFragment implements SeekBar.OnSeekBarChangeListener {
    private FragmentBottomPropertiesDialogBinding binding;
    private Properties mProperties;

    public PropertiesFragment() {
        // Required empty public constructor
    }

    public interface Properties {
        void onColorChanged(int colorCode);

        void onOpacityChanged(int opacity);

        void onShapeSizeChanged(int shapeSize);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBottomPropertiesDialogBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvColor = binding.rvColors;
        SeekBar sbOpacity = binding.sbOpacity;
        SeekBar sbBrushSize = binding.sbSize;

        sbOpacity.setOnSeekBarChangeListener(this);
        sbBrushSize.setOnSeekBarChangeListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvColor.setLayoutManager(layoutManager);
        rvColor.setHasFixedSize(true);
        ColorPickerAdapter colorPickerAdapter = new ColorPickerAdapter(requireActivity());
        colorPickerAdapter.setOnColorPickerClickListener(colorCode->{
            if (mProperties != null) {
                dismiss();
                mProperties.onColorChanged(colorCode);
            }
        });
        rvColor.setAdapter(colorPickerAdapter);
    }

    public void setPropertiesChangeListener(Properties properties) {
        mProperties = properties;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        switch (seekBar.getId()) {
            case R.id.sbOpacity:
                if (mProperties != null) {
                    mProperties.onOpacityChanged(i);
                }
                break;
            case R.id.sbSize:
                if (mProperties != null) {
                    mProperties.onShapeSizeChanged(i);
                }
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
