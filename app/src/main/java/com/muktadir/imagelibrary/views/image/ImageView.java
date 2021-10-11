package com.muktadir.imagelibrary.views.image;

import static com.muktadir.imagelibrary.utils.Constrains.EDIT_IMAGE;
import static com.muktadir.imagelibrary.utils.Constrains.VIEW_IMAGE;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.muktadir.imagelibrary.R;
import com.muktadir.imagelibrary.databinding.FragmentImageViewBinding;
import com.muktadir.imagelibrary.domain.models.Image;

import java.io.File;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link DaggerFragment} subclass.
 */
public class ImageView extends DaggerFragment {
    private static final String TAG = "";
    private FragmentImageViewBinding binding;
    private Image image;

    private NavController navController;

    @Inject
    RequestManager requestManager;

    public ImageView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            image = getArguments().getParcelable(VIEW_IMAGE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentImageViewBinding.inflate(inflater,container,false);
        ActionBar actionBar = ((AppCompatActivity)requireActivity()).getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
            exitFullscreen();
        }
        if(image != null){
           requestManager.load(image.getUri()).into(binding.imageView);
           String title = new File(image.getUri().getPath()).getName();
           binding.title.setText(title);
        }
        return binding.getRoot();
    }

    private void exitFullscreen(){
        requireActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN); // Disable fullscreen mode
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        binding.backBtn.setOnClickListener((v)->{
            navController.popBackStack();
        });
        binding.shareBtn.setOnClickListener((v)->{
            if(image != null){
                shareWithGlide(image.getUri());
            }
        });
        binding.editButton.setOnClickListener((v)->{
            if(image != null){
                Bundle bundle =  new Bundle();
                bundle.putParcelable(EDIT_IMAGE,image);
                navController.navigate(R.id.action_imageView_to_editImageView,bundle);
            }
        });
    }

    private void shareWithGlide(Uri uri){
        Glide.with(requireContext())
                .asBitmap()
                .load(uri)
                .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_TEXT, "Hey, this is your edited image");
                        String path = MediaStore.Images.Media.insertImage(requireContext().getContentResolver(), resource, "", null);
                        Uri screenshotUri = Uri.parse(path);
                        intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                        intent.setType("image/*");
                        startActivity(Intent.createChooser(intent, "Share image via..."));
                        File file = new File(screenshotUri.getPath());
                        boolean del = file.delete();
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }
}