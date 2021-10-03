package com.muktadir.imagelibrary.domain.models;

import android.net.Uri;

import java.time.LocalDateTime;

public class EditedImage {
    private String id;
    private Uri uri;
    private LocalDateTime createdAt;

    public EditedImage(String id, Uri uri, LocalDateTime createdAt) {
        this.id = id;
        this.uri = uri;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public Uri getUri() {
        return uri;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
