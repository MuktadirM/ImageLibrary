package com.muktadir.imagelibrary.views.image.photo.tools;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.muktadir.imagelibrary.R;
import com.muktadir.imagelibrary.databinding.RowEditingToolsBinding;

import java.util.ArrayList;
import java.util.List;

public class EditingToolsAdapter extends RecyclerView.Adapter<EditingToolsAdapter.ViewHolder> {
    private final List<Tool> mToolList = new ArrayList<>();
    private final OnItemSelectedToolType mOnItemSelected;

    public EditingToolsAdapter(OnItemSelectedToolType onItemSelected) {
        mOnItemSelected = onItemSelected;
        mToolList.add(new Tool("Shape", R.drawable.ic_oval, ToolType.SHAPE));
        mToolList.add(new Tool("Text", R.drawable.ic_text, ToolType.TEXT));
        mToolList.add(new Tool("Eraser", R.drawable.ic_eraser, ToolType.ERASER));
        mToolList.add(new Tool("Filter", R.drawable.ic_photo_filter, ToolType.FILTER));
        mToolList.add(new Tool("Emoji", R.drawable.ic_insert_emoticon, ToolType.EMOJI));
        mToolList.add(new Tool("Sticker", R.drawable.ic_sticker, ToolType.STICKER));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RowEditingToolsBinding binding = RowEditingToolsBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding,mOnItemSelected);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tool item = mToolList.get(position);
        holder.binding.setTool(item);
        Glide.with(holder.itemView.getContext()).load(item.getIcon()).into(holder.binding.image);
    }

    @Override
    public int getItemCount() {
        return mToolList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final RowEditingToolsBinding binding;
        private final OnItemSelectedToolType onItemSelected;
        ViewHolder(RowEditingToolsBinding binding,OnItemSelectedToolType onItemSelected) {
            super(binding.getRoot());
            this.binding = binding;
            this.onItemSelected = onItemSelected;
            itemView.setOnClickListener(this);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            onItemSelected.onToolSelected(mToolList.get(getLayoutPosition()).getToolType());
        }
    }
}
