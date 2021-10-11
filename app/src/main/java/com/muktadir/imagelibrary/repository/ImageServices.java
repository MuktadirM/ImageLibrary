package com.muktadir.imagelibrary.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.muktadir.imagelibrary.di.repository.LocalDatabaseModule;
import com.muktadir.imagelibrary.domain.models.EditedImage;
import com.muktadir.imagelibrary.domain.services.IImageServices;
import com.muktadir.imagelibrary.repository.local.LocalDatabase;
import com.muktadir.imagelibrary.repository.local.dto.EditedImageDto;
import com.muktadir.imagelibrary.repository.local.dto.UnSaveEditedImageDto;
import com.muktadir.imagelibrary.repository.local.entities.EditedImageEntity;
import com.muktadir.imagelibrary.repository.local.entities.UnSaveImageEntity;
import com.muktadir.imagelibrary.utils.Resource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

public class ImageServices implements IImageServices {
    private MutableLiveData<Resource<List<EditedImage>>> liveDataEditedImages;
    private MutableLiveData<Resource<EditedImage>> liveDataEditedUnSave;
    private MutableLiveData<Resource<Boolean>> resourceMutableLiveData;
    private final LocalDatabase localDatabase;
    private List<EditedImage> editedImageList;

    @Inject
    EditedImageDto dto;

    @Inject
    UnSaveEditedImageDto unSaveDto;

    @Inject
    public ImageServices(LocalDatabase localDatabase) {
        this.localDatabase = localDatabase;
    }

    @Override
    public LiveData<Resource<List<EditedImage>>> getAllEditedImages() {
        if(liveDataEditedImages == null){
            liveDataEditedImages = new MutableLiveData<>();
        }
        if(editedImageList == null){
            editedImageList = new ArrayList<>();
        }
       try {
           editedImageList = dto.toDomainList(localDatabase.getEditedImageDao().getAll());
           liveDataEditedImages.postValue(Resource.success(editedImageList));
       } catch (Exception e){
           liveDataEditedImages.postValue(Resource.error("Sorry"+e.getMessage(),null));
       }
        return liveDataEditedImages;
    }

    @Override
    public LiveData<Resource<Boolean>> delete(EditedImage image) {
        if(resourceMutableLiveData == null){
            resourceMutableLiveData = new MutableLiveData<>();
        }
        try {
            EditedImageEntity entity = dto.toEntity(image);
            localDatabase.getEditedImageDao().deleteOne(entity);
            resourceMutableLiveData.postValue(Resource.success(true));
        } catch (Exception e){
            resourceMutableLiveData.postValue(Resource.error("Error "+e.getMessage(),null));
        }
        return resourceMutableLiveData;
    }

    @Override
    public LiveData<Resource<Boolean>> saveImage(EditedImage image) {
        if(resourceMutableLiveData == null){
            resourceMutableLiveData = new MutableLiveData<>();
        }
        image.setNew(false);
        image.setCreatedAt(Calendar.getInstance().getTime());
        EditedImageEntity entity = dto.toEntity(image);
        try {

            localDatabase.getEditedImageDao().insertOne(entity);
            resourceMutableLiveData.postValue(Resource.success(true));
        } catch (Exception e){
            resourceMutableLiveData.postValue(Resource.error("Error "+e.getMessage(),null));
        }
        return resourceMutableLiveData;
    }

    @Override
    public LiveData<Resource<Boolean>> saveWork(EditedImage image) {
        if(resourceMutableLiveData == null){
            resourceMutableLiveData = new MutableLiveData<>();
        }
        image.setCreatedAt(Calendar.getInstance().getTime());
        UnSaveImageEntity entity = unSaveDto.toEntity(image);

        if(image.isNew()){
            localDatabase.getUnSaveImageDao().deleteAll();
            try {
                localDatabase.getUnSaveImageDao().insertOne(entity);
                resourceMutableLiveData.postValue(Resource.success(true));
            } catch (Exception e){
                resourceMutableLiveData.postValue(Resource.error("Error "+e.getMessage(),null));
            }
        } else {
            try {
                localDatabase.getUnSaveImageDao().updateOne(entity);
                resourceMutableLiveData.postValue(Resource.success(true));
            } catch (Exception e){
                resourceMutableLiveData.postValue(Resource.error("Error "+e.getMessage(),null));
            }
        }
        return resourceMutableLiveData;
    }

    @Override
    public LiveData<Resource<EditedImage>> getUnSaveWork() {
        List<EditedImage> all = new ArrayList<>();

        if(liveDataEditedUnSave == null){
            liveDataEditedUnSave = new MutableLiveData<>();
        }
        liveDataEditedUnSave.postValue(Resource.loading(null));
        try {
            all = unSaveDto.toDomainList(localDatabase.getUnSaveImageDao().getAll());
            if(all.size()>0){
                liveDataEditedUnSave.postValue(Resource.success(all.get(0)));
            }
        } catch (Exception e){
            liveDataEditedUnSave.postValue(Resource.error("Error "+e.getMessage(),null));
        }
        return liveDataEditedUnSave;
    }
}
