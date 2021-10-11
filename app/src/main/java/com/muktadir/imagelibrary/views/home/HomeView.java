package com.muktadir.imagelibrary.views.home;

import static com.muktadir.imagelibrary.utils.Constrains.EDIT_IMAGE;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.RequestManager;
import com.muktadir.imagelibrary.R;
import com.muktadir.imagelibrary.databinding.FragmentHomeBinding;
import com.muktadir.imagelibrary.domain.models.EditedImage;
import com.muktadir.imagelibrary.domain.models.Image;
import com.muktadir.imagelibrary.viewModels.ImageViewModel;
import com.muktadir.imagelibrary.viewModels.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link DaggerFragment} subclass.
 */
public class HomeView extends DaggerFragment {
    private ImageViewModel viewModel;
    private static final String TAG = "HomeView";
    private FragmentHomeBinding binding;
    private NavController navController;
    private static final String FILE_PROVIDER_AUTH = "com.muktadir.imagelibrary.fileProvider";
    private Uri FILE_URI;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    RequestManager requestManager;

    public HomeView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this,providerFactory).get(ImageViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater,container,false);
       ActionBar actionBar = ((AppCompatActivity)requireActivity()).getSupportActionBar();
       if(actionBar != null){
           actionBar.hide();
           exitFullscreen();
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
        binding.listItem.setOnClickListener(v->{
            navController.navigate(R.id.action_homeView_to_listImageView);
        });

        binding.selectImageFromBtn.setOnClickListener(v->{
            pickImage();
        });
        binding.editImageBtn.setOnClickListener(v -> {
            if(FILE_URI != null){
                Bundle bundle = new Bundle();
                Image image = new Image();
                image.setNew(true);
                image.setUri(FILE_URI);
                bundle.putParcelable(EDIT_IMAGE,image);
                navController.navigate(R.id.action_homeView_to_editImageView,bundle);
            }
        });
         binding.selectedImageView.setOnClickListener(v -> {
             pickImage();
         });

         viewModel.getAllEditedImages().removeObservers(this);
         viewModel.getAllEditedImages().observe(getViewLifecycleOwner(),(listResource -> {
             if(listResource != null){
                 switch (listResource.status){
                     case SUCCESS:
                            if(listResource.data != null){
                                loadImage(listResource.data);
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

         viewModel.getLastWork().removeObservers(this);
         viewModel.getLastWork().observe(getViewLifecycleOwner(),(editedImageResource -> {
             if(editedImageResource != null){
                 switch (editedImageResource.status){
                     case SUCCESS:
                         if(editedImageResource.data != null){
                             loadLastWork(editedImageResource.data);
                         }
                         break;
                     case ERROR:
                         Log.d(TAG, "onViewCreated: "+editedImageResource.message);
                         break;
                     case LOADING:
                         Log.d(TAG, "onViewCreated: Loading Last work....");
                         break;
                 }
             }
         }));
    }

    private void loadLastWork(EditedImage data) {
        requestManager.load(data.getUri()).into(binding.lastEditUnSaveImageView);
        String title ="Modified "+ data.diffForHuman();
        binding.titleUnSaveTxt.setText(title);
        Log.d(TAG, "loadLastWork: "+data.getUri());
    }

    private void hidePickButton(){
        if(FILE_URI != null){
            binding.editImageBtn.setVisibility(View.VISIBLE);
            binding.selectImageFromBtn.setVisibility(View.INVISIBLE);
        }else {
            binding.editImageBtn.setVisibility(View.INVISIBLE);
            binding.selectImageFromBtn.setVisibility(View.VISIBLE);
        }

    }

    private void pickImage() {

        String[] mimeTypes =
                {"image/*"};

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        intent.setType(mimeTypes[0]);
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        imagePickActivityResultLauncher.launch(Intent.createChooser(intent, "Pick an image"));
    }

    ActivityResultLauncher<Intent> imagePickActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();

                        if (data == null) {
                            hidePickButton();
                            return;
                        }
                        try {
                            Uri fileReceived = data.getData();
                            FILE_URI = fileReceived;
                            requestManager.load(fileReceived).into(binding.selectedImageView);
                            hidePickButton();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            });


    private void loadImage(List<EditedImage> list){
        String txt = "No Image";
        binding.imageCountTxt.setText(txt);
        if (list.size()>0) {
            txt = list.size()>1?list.size()+" Images":"1 Image";
            binding.imageCountTxt.setText(txt);
            requestManager
                    .load(list.get(0).getUri())
                    .into(binding.allEditedImageView);
        }
    }

}