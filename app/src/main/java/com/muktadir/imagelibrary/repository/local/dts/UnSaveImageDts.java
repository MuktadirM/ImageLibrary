package com.muktadir.imagelibrary.repository.local.dts;

import com.muktadir.imagelibrary.domain.models.UnSaveImage;
import com.muktadir.imagelibrary.repository.local.entities.UnSaveImageEntity;
import com.muktadir.imagelibrary.utils.IDataTransferObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UnSaveImageDts implements IDataTransferObject<UnSaveImage, UnSaveImageEntity> {

    @Override
    public UnSaveImageEntity toEntity(UnSaveImage entity) {
        return new UnSaveImageEntity(
                entity.getId(),
                entity.getName(),
                entity.getImagePath(),
                entity.getCreatedAt().toString(),
                entity.getUpdateAt().toString()
        );
    }

    @Override
    public UnSaveImage toDomain(UnSaveImageEntity entity) {
        return new UnSaveImage(
                entity.getId(),
                entity.getName(),
                entity.getImagePath(),
                getDateTime(entity.getCreatedAt()),
                getDateTime(entity.getUpdateAt())
        );
    }

    @Override
    public List<UnSaveImageEntity> toEntityList(List<UnSaveImage> entities) {
        List<UnSaveImageEntity> entityList = new ArrayList<>();
        for (UnSaveImage image :
                entities) {
            entityList.add(toEntity(image));

        }
        return entityList;
    }

    @Override
    public List<UnSaveImage> toDomainList(List<UnSaveImageEntity> unSaveImageEntities) {
        List<UnSaveImage> domainList = new ArrayList<>();
        for (UnSaveImageEntity image :
                unSaveImageEntities) {
            domainList.add(toDomain(image));
        }
        return domainList;
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
