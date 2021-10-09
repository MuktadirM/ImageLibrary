package com.muktadir.imagelibrary.views.image.photo.colors;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.muktadir.imagelibrary.R;
import com.muktadir.imagelibrary.databinding.ColorPickerItemListBinding;

import java.util.ArrayList;
import java.util.List;

public class ColorPickerAdapter extends RecyclerView.Adapter<ColorPickerAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<Integer> colorPickerColors;
    private OnColorPickerClickListener onColorPickerClickListener;

    public ColorPickerAdapter(@NonNull Context context, @NonNull List<Integer> colorPickerColors) {
        this.context = context;
        this.colorPickerColors = colorPickerColors;
        this.inflater = LayoutInflater.from(context);
    }

    public ColorPickerAdapter(@NonNull Context context) {
        this(context, getDefaultColors(context));
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public interface OnColorPickerClickListener {
        void onColorPickerClickListener(int colorCode);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ColorPickerItemListBinding binding = ColorPickerItemListBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding,onColorPickerClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.colorPickerView.setBackgroundColor(colorPickerColors.get(position));
    }

    @Override
    public int getItemCount() {
        return colorPickerColors.size();
    }

    private void buildColorPickerView(View view, int colorCode) {
        view.setVisibility(View.VISIBLE);

        ShapeDrawable biggerCircle = new ShapeDrawable(new OvalShape());
        biggerCircle.setIntrinsicHeight(20);
        biggerCircle.setIntrinsicWidth(20);
        biggerCircle.setBounds(new Rect(0, 0, 20, 20));
        biggerCircle.getPaint().setColor(colorCode);

        ShapeDrawable smallerCircle = new ShapeDrawable(new OvalShape());
        smallerCircle.setIntrinsicHeight(5);
        smallerCircle.setIntrinsicWidth(5);
        smallerCircle.setBounds(new Rect(0, 0, 5, 5));
        smallerCircle.getPaint().setColor(Color.WHITE);
        smallerCircle.setPadding(10, 10, 10, 10);
        Drawable[] drawables = {smallerCircle, biggerCircle};

        LayerDrawable layerDrawable = new LayerDrawable(drawables);
        view.setBackground(layerDrawable);
    }

    public void setOnColorPickerClickListener(OnColorPickerClickListener onColorPickerClickListener) {
        this.onColorPickerClickListener = onColorPickerClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final ColorPickerItemListBinding binding;
        private final OnColorPickerClickListener onColorPickerClickListener;

        public ViewHolder(ColorPickerItemListBinding binding,OnColorPickerClickListener onColorPickerClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onColorPickerClickListener = onColorPickerClickListener;
            itemView.setOnClickListener(this);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            if(onColorPickerClickListener != null){
            onColorPickerClickListener.onColorPickerClickListener(colorPickerColors.get(getAbsoluteAdapterPosition()));
            }
        }
    }

    public static List<Integer> getDefaultColors(Context context) {
        ArrayList<Integer> colorPickerColors = new ArrayList<>();
        colorPickerColors.add(ContextCompat.getColor(context, R.color.blue_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.brown_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.green_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.orange_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.red_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.black));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.red_orange_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.sky_blue_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.violet_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.white));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.yellow_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.yellow_green_color_picker));
        return colorPickerColors;
    }
}
