package com.muktadir.imagelibrary.repository.local.dts;

import android.net.Uri;

import com.muktadir.imagelibrary.domain.models.EditedImage;
import com.muktadir.imagelibrary.repository.local.entities.EditedImageEntity;
import com.muktadir.imagelibrary.utils.IDataTransferObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EditedImageDts implements IDataTransferObject<EditedImage, EditedImageEntity> {

    @Override
    public EditedImageEntity toEntity(EditedImage entity) {
        return new EditedImageEntity(
                entity.getId(),
                entity.getUri().toString(),
                entity.getTitle(),
                entity.getCreatedAt().toString()
        );
    }

    @Override
    public EditedImage toDomain(EditedImageEntity entity) {
        return new EditedImage(
                entity.getId(),
                entity.getTitle(),
                Uri.parse(entity.getUrl()),
                getDateTime(entity.getCreatedAt())
        );
    }

    @Override
    public List<EditedImageEntity> toEntityList(List<EditedImage> entities) {
        List<EditedImageEntity> list = new ArrayList<>();
        for (EditedImage image : entities) {
         list.add(toEntity(image));
        }
        return list;
    }

    @Override
    public List<EditedImage> toDomainList(List<EditedImageEntity> editedImageEntities) {
        List<EditedImage> list = new ArrayList<>();
        for (EditedImageEntity image : editedImageEntities) {
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
