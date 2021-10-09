package com.muktadir.imagelibrary.views.image.edit;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.muktadir.imagelibrary.utils.Constrains.EDIT_IMAGE;
import static com.muktadir.imagelibrary.views.image.photo.core.FileSaveHelper.isSdkHigherThan28;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.ChangeBounds;
import androidx.transition.TransitionManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.muktadir.imagelibrary.R;
import com.muktadir.imagelibrary.databinding.FragmentEditImageViewBinding;
import com.muktadir.imagelibrary.domain.models.EditedImage;
import com.muktadir.imagelibrary.domain.models.Image;
import com.muktadir.imagelibrary.viewModels.ImageViewModel;
import com.muktadir.imagelibrary.viewModels.ViewModelProviderFactory;
import com.muktadir.imagelibrary.views.image.photo.core.EmojiFragment;
import com.muktadir.imagelibrary.views.image.photo.core.FileSaveHelper;
import com.muktadir.imagelibrary.views.image.photo.core.PropertiesFragment;
import com.muktadir.imagelibrary.views.image.photo.core.ShapeFragment;
import com.muktadir.imagelibrary.views.image.photo.core.StickerFragment;
import com.muktadir.imagelibrary.views.image.photo.core.TextEditorDialogFragment;
import com.muktadir.imagelibrary.views.image.photo.emoji.EmojiListener;
import com.muktadir.imagelibrary.views.image.photo.filters.FilterViewAdapter;
import com.muktadir.imagelibrary.views.image.photo.filters.OnItemSelectedFilter;
import com.muktadir.imagelibrary.views.image.photo.sticker.StickerListener;
import com.muktadir.imagelibrary.views.image.photo.tools.EditingToolsAdapter;
import com.muktadir.imagelibrary.views.image.photo.tools.OnItemSelectedToolType;
import com.muktadir.imagelibrary.views.image.photo.tools.ToolType;
import com.muktadir.photoeditor.OnPhotoEditorListener;
import com.muktadir.photoeditor.PhotoEditor;
import com.muktadir.photoeditor.PhotoEditorView;
import com.muktadir.photoeditor.SaveSettings;
import com.muktadir.photoeditor.TextStyleBuilder;
import com.muktadir.photoeditor.shape.ShapeBuilder;
import com.muktadir.photoeditor.utils.PhotoFilter;
import com.muktadir.photoeditor.utils.ShapeType;
import com.muktadir.photoeditor.utils.ViewType;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link DaggerFragment} subclass.
 * create an instance of this fragment.
 */
public class EditImageView extends DaggerFragment implements OnPhotoEditorListener,View.OnClickListener,
        PropertiesFragment.Properties,
        ShapeFragment.Properties,
        EmojiListener,
        StickerListener,
        OnItemSelectedToolType, // EditingToolsAdapter.OnItemSelected
        OnItemSelectedFilter {
    private Image image;
    private ProgressDialog mProgressDialog;
    private NavController navController;
    private static final String TAG = EditImageView.class.getSimpleName();
    private FragmentEditImageViewBinding binding;

    public static final String FILE_PROVIDER_AUTHORITY = "com.muktadir.imagelibrary.fileProvider";
    private static final int CAMERA_REQUEST = 52;
    private static final int PICK_REQUEST = 53;
    public static final String ACTION_NEXTGEN_EDIT = "action_nextgen_edit";
    public static final String PINCH_TEXT_SCALABLE_INTENT_KEY = "PINCH_TEXT_SCALABLE";

    private ImageViewModel viewModel;
    @Inject
    ViewModelProviderFactory providerFactory;

    @Nullable
    @VisibleForTesting
    Uri mSaveImageUri;

    Uri saveImageUri;

    PhotoEditor mPhotoEditor;
    private PhotoEditorView mPhotoEditorView;
    private PropertiesFragment mPropertiesFragment;
    private ShapeFragment mShapeFragment;
    private ShapeBuilder mShapeBuilder;
    private EmojiFragment mEmojiFragment;
    private StickerFragment mStickerFragment;
    private TextView mTxtCurrentTool;
    private Typeface mWonderFont;
    private RecyclerView mRvTools, mRvFilters;
    private final EditingToolsAdapter mEditingToolsAdapter = new EditingToolsAdapter(this);
    private final FilterViewAdapter mFilterViewAdapter = new FilterViewAdapter(this);
    private ConstraintLayout mRootView;
    private final ConstraintSet mConstraintSet = new ConstraintSet();
    private boolean mIsFilterVisible;

    private FileSaveHelper mSaveFileHelper;

    public EditImageView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            image = getArguments().getParcelable(EDIT_IMAGE);
        }
        viewModel = new ViewModelProvider(this,providerFactory).get(ImageViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditImageViewBinding.inflate(inflater,container,false);
        initViews();

        handleIntentImage(mPhotoEditorView.getSource());

        mWonderFont = Typeface.createFromAsset(requireActivity().getAssets(), "beyond_wonderland.ttf");

        mPropertiesFragment = new PropertiesFragment();
        mEmojiFragment = new EmojiFragment();
        mStickerFragment = new StickerFragment();
        mShapeFragment = new ShapeFragment();
        mStickerFragment.setStickerListener(this);
        mEmojiFragment.setEmojiListener(this);
        mPropertiesFragment.setPropertiesChangeListener(this);
        mShapeFragment.setPropertiesChangeListener(this);

        LinearLayoutManager llmTools = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRvTools.setLayoutManager(llmTools);
        mRvTools.setAdapter(mEditingToolsAdapter);

        LinearLayoutManager llmFilters = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRvFilters.setLayoutManager(llmFilters);
        mRvFilters.setAdapter(mFilterViewAdapter);

        // NOTE(lucianocheng): Used to set integration testing parameters to PhotoEditor
        boolean pinchTextScalable = requireActivity().getIntent().getBooleanExtra(PINCH_TEXT_SCALABLE_INTENT_KEY, true);

        mPhotoEditor = new PhotoEditor.Builder(requireContext(), mPhotoEditorView)
                .setPinchTextScalable(pinchTextScalable) // set flag to make text scalable when pinch
                //.setDefaultTextTypeface(mTextRobotoTf)
                //.setDefaultEmojiTypeface(mEmojiTypeFace)
                .build(); // build photo editor sdk

        mPhotoEditor.setOnPhotoEditorListener(this);

        //Set Image Dynamically
        mPhotoEditorView.getSource().setImageURI(image.getUri());
        mSaveFileHelper = new FileSaveHelper((AppCompatActivity) requireActivity());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    private void initViews() {
        mPhotoEditorView = binding.photoEditorView;
        mRvTools = binding.rvConstraintTools;
        mRvFilters = binding.rvFilterView;
        mTxtCurrentTool = binding.txtCurrentTool;
        mRootView = binding.rootView;

        binding.imgUndo.setOnClickListener(this);
        binding.imgRedo.setOnClickListener(this);
        binding.imgSave.setOnClickListener(this);
        binding.imgClose.setOnClickListener(this);
        binding.imgShare.setOnClickListener(this);
    }

    @Override
    public void onEditTextChangeListener(View rootView, String text, int colorCode) {
        TextEditorDialogFragment textEditorDialogFragment =
                TextEditorDialogFragment.show((AppCompatActivity) requireActivity(), text, colorCode);
        textEditorDialogFragment.setOnTextEditorListener((inputText, newColorCode) -> {
            final TextStyleBuilder styleBuilder = new TextStyleBuilder();
            styleBuilder.withTextColor(newColorCode);

            mPhotoEditor.editText(rootView, inputText, styleBuilder);
            mTxtCurrentTool.setText(R.string.label_text);
        });
    }

    @Override
    public void onAddViewListener(ViewType viewType, int numberOfAddedViews) {

    }

    @Override
    public void onRemoveViewListener(ViewType viewType, int numberOfAddedViews) {

    }

    @Override
    public void onStartViewChangeListener(ViewType viewType) {

    }

    @Override
    public void onStopViewChangeListener(ViewType viewType) {

    }

    @Override
    public void onTouchSourceImage(MotionEvent event) {

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imgUndo:
                mPhotoEditor.undo();
                break;

            case R.id.imgRedo:
                mPhotoEditor.redo();
                break;

            case R.id.imgSave:
               saveImage();
                break;

            case R.id.imgClose:
                onBackPressed();
                break;
            case R.id.imgShare:
                shareImage();
                break;
        }
    }

    @Override
    public void onColorChanged(int colorCode) {
        mPhotoEditor.setShape(mShapeBuilder.withShapeColor(colorCode));
        mTxtCurrentTool.setText(R.string.label_brush);
    }

    @Override
    public void onOpacityChanged(int opacity) {
        mPhotoEditor.setShape(mShapeBuilder.withShapeOpacity(opacity));
        mTxtCurrentTool.setText(R.string.label_brush);
    }

    @Override
    public void onShapeSizeChanged(int shapeSize) {
        mPhotoEditor.setShape(mShapeBuilder.withShapeSize(shapeSize));
        mTxtCurrentTool.setText(R.string.label_brush);
    }

    @Override
    public void onShapePicked(ShapeType shapeType) {
        mPhotoEditor.setShape(mShapeBuilder.withShapeType(shapeType));
    }

    @Override
    public void onToolSelected(ToolType itemSelected) {
        switch (itemSelected) {
            case SHAPE:
                mPhotoEditor.setBrushDrawingMode(true);
                mShapeBuilder = new ShapeBuilder();
                mPhotoEditor.setShape(mShapeBuilder);
                mTxtCurrentTool.setText(R.string.label_shape);
                showBottomSheetDialogFragment(mShapeFragment);
                break;
            case TEXT:
                TextEditorDialogFragment textEditorDialogFragment = TextEditorDialogFragment.show((AppCompatActivity) requireActivity());
                textEditorDialogFragment.setOnTextEditorListener((inputText, colorCode) -> {
                    final TextStyleBuilder styleBuilder = new TextStyleBuilder();
                    styleBuilder.withTextColor(colorCode);

                    mPhotoEditor.addText(inputText, styleBuilder);
                    mTxtCurrentTool.setText(R.string.label_text);
                });
                break;
            case ERASER:
                mPhotoEditor.brushEraser();
                mTxtCurrentTool.setText(R.string.label_eraser_mode);
                break;
            case FILTER:
                mTxtCurrentTool.setText(R.string.label_filter);
                showFilter(true);
                break;
            case EMOJI:
                showBottomSheetDialogFragment(mEmojiFragment);
                break;
            case STICKER:
                showBottomSheetDialogFragment(mStickerFragment);
                break;
        }
    }

    private void showBottomSheetDialogFragment(BottomSheetDialogFragment fragment) {
        if (fragment == null || fragment.isAdded()) {
            return;
        }
        fragment.show(getChildFragmentManager(), fragment.getTag());
    }

    @Override
    public void onFilterSelected(PhotoFilter photoFilter) {
        mPhotoEditor.setFilterEffect(photoFilter);
    }

    void showFilter(boolean isVisible) {
        mIsFilterVisible = isVisible;
        mConstraintSet.clone(mRootView);

        if (isVisible) {
            mConstraintSet.clear(mRvFilters.getId(), ConstraintSet.START);
            mConstraintSet.connect(mRvFilters.getId(), ConstraintSet.START,
                    ConstraintSet.PARENT_ID, ConstraintSet.START);
            mConstraintSet.connect(mRvFilters.getId(), ConstraintSet.END,
                    ConstraintSet.PARENT_ID, ConstraintSet.END);
        } else {
            mConstraintSet.connect(mRvFilters.getId(), ConstraintSet.START,
                    ConstraintSet.PARENT_ID, ConstraintSet.END);
            mConstraintSet.clear(mRvFilters.getId(), ConstraintSet.END);
        }

        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(350);
        changeBounds.setInterpolator(new AnticipateOvershootInterpolator(1.0f));
        TransitionManager.beginDelayedTransition(mRootView, changeBounds);

        mConstraintSet.applyTo(mRootView);
    }

    @Override
    public void onEmojiClick(String emojiUnicode) {
        mPhotoEditor.addEmoji(emojiUnicode);
        mTxtCurrentTool.setText(R.string.label_emoji);
    }

    @Override
    public void onStickerClick(Bitmap bitmap) {
        mPhotoEditor.addImage(bitmap);
        mTxtCurrentTool.setText(R.string.label_sticker);
    }

    private void handleIntentImage(ImageView source) {
        Intent intent = requireActivity().getIntent();
        if (intent != null) {
            // NOTE(lucianocheng): Using "yoda conditions" here to guard against
            //                     a null Action in the Intent.
            if (Intent.ACTION_EDIT.equals(intent.getAction()) ||
                    ACTION_NEXTGEN_EDIT.equals(intent.getAction())) {
                try {
                    Uri uri = intent.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), uri);
                    source.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                String intentType = intent.getType();
                if (intentType != null && intentType.startsWith("image/")) {
                    Uri imageUri = intent.getData();
                    if (imageUri != null) {
                        source.setImageURI(imageUri);
                    }
                }
            }
        }
    }

    private void showSaveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage(getString(R.string.msg_save_image));
        builder.setPositiveButton("Save", (dialog, which) -> saveImage());
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.setNeutralButton("Discard", (dialog, which) -> finish());
        builder.create().show();

    }

    private void saveImage(){
        final String fileName = System.currentTimeMillis() + ".png";
        final boolean hasStoragePermission =
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PERMISSION_GRANTED;
        if (hasStoragePermission || isSdkHigherThan28()) {
            showLoading("Saving...");
            mSaveFileHelper.createFile(fileName, (fileCreated, filePath, error, uri) -> {
                if (fileCreated) {
                    SaveSettings saveSettings = new SaveSettings.Builder()
                            .setClearViewsEnabled(true)
                            .setTransparencyEnabled(true)
                            .build();
                    String filePath2 = "";
                    mPhotoEditor.saveAsFile(filePath, saveSettings, new PhotoEditor.OnSaveListener() {
                        @Override
                        public void onSuccess(@NonNull String imagePath) {
                            mSaveFileHelper.notifyThatFileIsNowPubliclyAvailable(requireActivity().getContentResolver());
                            hideLoading();
                            showSnackBar("Image Saved Successfully");
                            mSaveImageUri = uri;
                            mPhotoEditorView.getSource().setImageURI(mSaveImageUri);
                            saveImageUri = Uri.parse("file:"+imagePath);
                            EditedImage editedImage = new EditedImage();
                            editedImage.setUri(saveImageUri);
                            editedImage.setTitle(new File(editedImage.getUri().getPath()).getName());
                            viewModel.saveImage(editedImage);
                        }

                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            hideLoading();
                            showSnackBar("Failed to save Image");
                        }
                    });

                } else {
                    hideLoading();
                    showSnackBar(error);
                }
            });
        } else {
            showSnackBar("Permission Error");
        }
    }

    private void shareImage() {
        if (saveImageUri == null) {
            showSnackBar(getString(R.string.msg_save_image_to_share));
            return;
        }
        shareWithGlide(saveImageUri);
    }

    private Uri buildFileProviderUri(@NonNull Uri uri) {
        return FileProvider.getUriForFile(requireContext(),
                FILE_PROVIDER_AUTHORITY,
                new File(uri.getPath()));
    }


    private void finish(){
        navController.popBackStack();
    }


    public void onBackPressed() {
        if (mIsFilterVisible) {
            showFilter(false);
            mTxtCurrentTool.setText(R.string.app_name);
        } else if (!mPhotoEditor.isCacheEmpty()) {
            showSaveDialog();
        } else {
            navController.popBackStack();
        }
    }

    protected void showLoading(@NonNull String message) {
        mProgressDialog = new ProgressDialog(requireContext());
        mProgressDialog.setMessage(message);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    protected void hideLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    private void showSnackBar(@NonNull String message) {
        View view = requireView().findViewById(android.R.id.content);
        if (view != null) {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        }
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