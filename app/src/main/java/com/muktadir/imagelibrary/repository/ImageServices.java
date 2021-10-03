package com.muktadir.imagelibrary.repository;

import androidx.lifecycle.LiveData;

import com.muktadir.imagelibrary.domain.models.EditedImage;
import com.muktadir.imagelibrary.domain.models.UnSaveImage;
import com.muktadir.imagelibrary.domain.services.IImageServices;
import com.muktadir.imagelibrary.utils.Resource;

import java.util.List;

import javax.inject.Inject;

public class ImageServices implements IImageServices {

    @Inject
    public ImageServices() {
    }

    @Override
    public LiveData<Resource<List<EditedImage>>> getAllEditedImages() {
        return null;
    }

    @Override
    public LiveData<Resource<Boolean>> delete(int Id) {
        return null;
    }

    @Override
    public LiveData<Resource<Boolean>> saveImage(EditedImage image) {
        return null;
    }

    @Override
    public LiveData<Resource<Boolean>> saveWork(UnSaveImage image) {
        return null;
    }

    @Override
    public LiveData<Resource<UnSaveImage>> getUnSaveWork() {
        return null;
    }
}
