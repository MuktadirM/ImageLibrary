package com.muktadir.imagelibrary.views.image.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.muktadir.imagelibrary.databinding.SingleImageItemBinding;
import com.muktadir.imagelibrary.domain.models.EditedImage;
import com.muktadir.imagelibrary.utils.ItemOnClick;

import java.util.ArrayList;
import java.util.List;

public class ImageListRecyclerAdapter extends RecyclerView.Adapter<ImageListRecyclerAdapter.ViewHolder> {
    private SingleImageItemBinding binding;
    private final Context context;
    private final ItemOnClick<EditedImage> onClick;
    private List<EditedImage> list;

    public ImageListRecyclerAdapter(Context context, ItemOnClick<EditedImage> onClick) {
        this.context = context;
        this.onClick = onClick;
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = SingleImageItemBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding,onClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setImage(list.get(position));
        Glide.with(context).load(list.get(position).getUri()).into(holder.binding.imageView);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setEditedImages(List<EditedImage> list)
    {
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final SingleImageItemBinding binding;
        private final ItemOnClick<EditedImage> onClick;
        public ViewHolder(SingleImageItemBinding binding,ItemOnClick<EditedImage> onClick) {
            super(binding.getRoot());
            this.binding = binding;
            this.onClick = onClick;
            itemView.setOnClickListener(this);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            onClick.ItemClicked(list.get(getAbsoluteAdapterPosition()),getAbsoluteAdapterPosition());
        }
    }
}
