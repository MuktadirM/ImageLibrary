package com.muktadir.imagelibrary.domain.services;

import androidx.lifecycle.LiveData;

import com.muktadir.imagelibrary.domain.models.EditedImage;
import com.muktadir.imagelibrary.domain.models.UnSaveImage;
import com.muktadir.imagelibrary.utils.Resource;

import java.util.List;

public interface IImageServices {
    public LiveData<Resource<List<EditedImage>>> getAllEditedImages();
    public LiveData<Resource<Boolean>> delete(int Id);
    public LiveData<Resource<Boolean>> saveImage(EditedImage image);
    public LiveData<Resource<Boolean>> saveWork(UnSaveImage image);
    public LiveData<Resource<UnSaveImage>> getUnSaveWork();
}
