package com.muktadir.imagelibrary.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.muktadir.imagelibrary.domain.models.EditedImage;
import com.muktadir.imagelibrary.domain.services.IImageServices;
import com.muktadir.imagelibrary.utils.Resource;

import java.util.List;

import javax.inject.Inject;

public class ImageViewModel extends ViewModel {
    private IImageServices services;

    @Inject
    public ImageViewModel(IImageServices services) {
        this.services = services;
    }

    public LiveData<Resource<Boolean>> saveImage(EditedImage image){
        return services.saveImage(image);
    }

    public LiveData<Resource<List<EditedImage>>> getAllEditedImages(){
        return services.getAllEditedImages();
    }

    public LiveData<Resource<Boolean>> lastEdit(EditedImage image){
        return services.saveWork(image);
    }

    public LiveData<Resource<EditedImage>> getLastWork(){
        return services.getUnSaveWork();
    }

    public LiveData<Resource<Boolean>> deleteImage(EditedImage image){
        return services.delete(image);
    }


}
