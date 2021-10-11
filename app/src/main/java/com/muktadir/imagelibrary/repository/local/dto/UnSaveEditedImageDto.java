package com.muktadir.imagelibrary.repository.local.dto;

import android.net.Uri;

import com.muktadir.imagelibrary.domain.models.EditedImage;
import com.muktadir.imagelibrary.repository.local.entities.UnSaveImageEntity;
import com.muktadir.imagelibrary.utils.IDataTransferObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UnSaveEditedImageDto implements IDataTransferObject<EditedImage, UnSaveImageEntity> {

    @Override
    public UnSaveImageEntity toEntity(EditedImage entity) {
        return new UnSaveImageEntity(
                entity.getId(),
                entity.getTitle(),
                entity.getUri().toString(),
                entity.getCreatedAt().toString()
        );
    }

    @Override
    public EditedImage toDomain(UnSaveImageEntity unSaveImageEntity) {
        return new EditedImage(
                unSaveImageEntity.getId(),
                Uri.parse(unSaveImageEntity.getUri()),
                false,
                unSaveImageEntity.getTitle(),
                getDateTime(unSaveImageEntity.getCreatedAt())
        );
    }

    @Override
    public List<UnSaveImageEntity> toEntityList(List<EditedImage> entities) {
        List<UnSaveImageEntity> list = new ArrayList<>();
        for (EditedImage image : entities) {
            list.add(toEntity(image));
        }
        return list;
    }

    @Override
    public List<EditedImage> toDomainList(List<UnSaveImageEntity> unSaveImageEntities) {
        List<EditedImage> list = new ArrayList<>();
        for (UnSaveImageEntity image : unSaveImageEntities) {
            list.add(toDomain(image));
        }
        return list;
    }

    private Date getDateTime(String dateTime){
        DateFormat formatter = new SimpleDateFormat("d-MMM-yyyy,HH:mm:ss aaa", Locale.getDefault());
        try {
            return formatter.parse(dateTime);
        } catch (Exception e){
            return Calendar.getInstance().getTime();
        }
    }
}
