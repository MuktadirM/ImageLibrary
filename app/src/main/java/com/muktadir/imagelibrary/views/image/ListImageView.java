package com.muktadir.imagelibrary.views.image;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.muktadir.imagelibrary.R;
import com.muktadir.imagelibrary.databinding.FragmentListImageViewBinding;
import com.muktadir.imagelibrary.domain.models.EditedImage;
import com.muktadir.imagelibrary.utils.ItemOnClick;
import com.muktadir.imagelibrary.viewModels.ImageViewModel;
import com.muktadir.imagelibrary.viewModels.ViewModelProviderFactory;
import com.muktadir.imagelibrary.views.image.adapter.ImageListRecyclerAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListImageView extends DaggerFragment implements ItemOnClick<EditedImage> {
    private static final String TAG = "onCreate";
    private FragmentListImageViewBinding binding;
    private ImageListRecyclerAdapter adapter;
    private ImageViewModel viewModel;
    private NavController navController;

    @Inject
    ViewModelProviderFactory providerFactory;

    public ListImageView() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Log.d(TAG, "onCreate: ");
        }
        viewModel = new ViewModelProvider(this,providerFactory).get(ImageViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListImageViewBinding.inflate(inflater,container,false);
        // Inflate the layout for this fragment
        adapter = new ImageListRecyclerAdapter(requireContext(),this);
        binding.imageListRecycler.setAdapter(adapter);
        return binding.getRoot();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        viewModel.getAllEditedImages().removeObservers(this);
        viewModel.getAllEditedImages().observe(getViewLifecycleOwner(),(listResource -> {
            if(listResource != null){
                switch (listResource.status){
                    case SUCCESS:
                        if(listResource.data != null){
                            adapter.setEditedImages(listResource.data);
                            adapter.notifyDataSetChanged();
                        }
                        break;
                    case ERROR:
                        Log.d(TAG, "onViewCreated: Data load failure "+listResource.message);
                        break;
                    case LOADING:
                        Log.d(TAG, "onViewCreated: Please wait loading");
                        break;
                }
            }
        }));
    }

    private void loadImage(){
        ArrayList<Uri> images = new ArrayList<>();
        File[] listFile;
        File file= new File(android.os.Environment.getExternalStorageDirectory(),"Pictures");
        if(file.isDirectory()){
            listFile = file.listFiles();
            assert listFile != null;
            Log.d(TAG, "loadImage: "+listFile.length);
            for (File value : listFile) {
                if(value.isFile()){
                    Uri uri = Uri.parse(value.toURI().toString());
                    String type = getMimeType(requireContext(),uri);
                    if(type != null && type.equals("image/jpeg")){
                        images.add(uri);
                    }
                }
            }
        }
        adapter.setEditedImages(getImageList(images));
    }

    private String getMimeType(Context context, Uri uri) {
        String mimeType = null;
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())) {
            ContentResolver cr = context.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }

    private List<EditedImage> getImageList(ArrayList<Uri> images){
        List<EditedImage> list = new ArrayList<>();
        int id = 0;
        for (Uri uri : images) {
            id = id+1;
            list.add(new EditedImage(id,uri,false,new File(uri.getPath()).getName(), Calendar.getInstance().getTime()));
        }
        return list;
    }

    @Override
    public void ItemClicked(EditedImage item, int position) {
       // shareWithGlide(Uri uri);
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
                        String path = MediaStore.Images.Media.insertImage(requireContext().getContentResolver(), resource, "temp_image", "Shareable temporary image");
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