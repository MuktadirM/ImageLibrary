package com.muktadir.imagelibrary.repository.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "un_save_images")
public class UnSaveImageEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "image_uri")
    private String uri;

    @ColumnInfo(name = "created_at")
    private String createdAt;

    public UnSaveImageEntity(int id, String name, String uri, String createdAt) {
        this.id = id;
        this.name = name;
        this.uri = uri;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String imagePath) {
        this.uri = imagePath;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
