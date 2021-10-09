package com.muktadir.imagelibrary.domain.models;

import static com.muktadir.imagelibrary.utils.Constrains.humanDiff;

import android.net.Uri;

import androidx.annotation.Nullable;

import java.util.Date;

public class
EditedImage extends Image{
    private String title;
    private Date createdAt;

    public EditedImage(){ super();}

    public EditedImage(int id,Uri uri,boolean isNew, String title, Date createdAt) {
        super(id, uri, isNew);
        this.title = title;
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public String diffForHuman(){
        return humanDiff(this.createdAt);
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
