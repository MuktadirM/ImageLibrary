package com.muktadir.imagelibrary.domain.models;

import androidx.annotation.Nullable;

import java.util.Date;

public class UnSaveImage {
    private int id;
    private String name;
    private String imagePath;
    private Date createdAt;
    private Date updateAt;

    public UnSaveImage(int id, String name, String imagePath, Date createdAt,Date updateAt)
    {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }

    public UnSaveImage copyWith(
            int id,@Nullable String name,@Nullable String imagePath,
            @Nullable Date createdAt,@Nullable Date updateAt)
    {

        return new UnSaveImage(id==-1?this.id:id,
                name == null?this.name:name,imagePath==null?this.imagePath:imagePath,
                createdAt==null?this.createdAt:createdAt,updateAt==null?this.updateAt:updateAt
                );
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }
}
