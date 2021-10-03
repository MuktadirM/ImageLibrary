package com.muktadir.imagelibrary.domain.models;

import static com.muktadir.imagelibrary.utils.Constrains.humanDiff;

import android.net.Uri;

import java.time.LocalDateTime;

public class EditedImage {
    private int id;
    private String title;
    private Uri uri;
    private LocalDateTime createdAt;

    public EditedImage(int id, String title, Uri uri, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.uri = uri;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Uri getUri() {
        return uri;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String diffForHuman(){
        return humanDiff(this.createdAt);
    }
}
