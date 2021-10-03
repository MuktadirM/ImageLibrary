package com.muktadir.imagelibrary.domain.models;

import androidx.annotation.Nullable;

import java.time.LocalDateTime;

public class UnSaveImage {
    private int id;
    private String name;
    private String imagePath;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public UnSaveImage(int id, String name, String imagePath, LocalDateTime createdAt,LocalDateTime updateAt)
    {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }

    public UnSaveImage copyWith(
            int id,@Nullable String name,@Nullable String imagePath,
            @Nullable LocalDateTime createdAt,@Nullable LocalDateTime updateAt)
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }
}
