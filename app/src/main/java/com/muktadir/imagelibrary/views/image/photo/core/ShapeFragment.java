package com.muktadir.imagelibrary.views.image.photo.core;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.muktadir.imagelibrary.R;
import com.muktadir.imagelibrary.databinding.FragmentBottomShapesDialogBinding;
import com.muktadir.imagelibrary.views.image.photo.colors.ColorPickerAdapter;
import com.muktadir.photoeditor.utils.ShapeType;

public class ShapeFragment extends BottomSheetDialogFragment implements SeekBar.OnSeekBarChangeListener {
    private FragmentBottomShapesDialogBinding binding;

    public ShapeFragment() {
        // Required empty public constructor
    }

    private Properties mProperties;

    public interface Properties {
        void onColorChanged(int colorCode);

        void onOpacityChanged(int opacity);

        void onShapeSizeChanged(int shapeSize);

        void onShapePicked(ShapeType shapeType);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBottomShapesDialogBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvColor = binding.shapeColors;
        SeekBar sbOpacity = binding.shapeOpacity;
        SeekBar sbBrushSize = binding.shapeSize;
        RadioGroup shapeGroup = binding.shapeRadioGroup;

        // shape picker
        shapeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.lineRadioButton) {
                mProperties.onShapePicked(ShapeType.LINE);
            } else if (checkedId == R.id.ovalRadioButton) {
                mProperties.onShapePicked(ShapeType.OVAL);
            } else if (checkedId == R.id.rectRadioButton) {
                mProperties.onShapePicked(ShapeType.RECTANGLE);
            } else {
                mProperties.onShapePicked(ShapeType.BRUSH);
            }
        });

        sbOpacity.setOnSeekBarChangeListener(this);
        sbBrushSize.setOnSeekBarChangeListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvColor.setLayoutManager(layoutManager);
        rvColor.setHasFixedSize(true);
        ColorPickerAdapter colorPickerAdapter = new ColorPickerAdapter(requireActivity());
        colorPickerAdapter.setOnColorPickerClickListener(colorCode -> {
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

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        switch (seekBar.getId()) {
            case R.id.shapeOpacity:
                if (mProperties != null) {
                    mProperties.onOpacityChanged(i);
                }
                break;
            case R.id.shapeSize:
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

