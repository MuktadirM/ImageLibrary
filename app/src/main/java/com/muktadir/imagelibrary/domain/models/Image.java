package com.muktadir.imagelibrary.domain.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable {
    private int id;
    private Uri uri;
    private boolean isNew;

    public Image() {
    }

    public Image(int id, Uri uri, boolean isNew) {
        this.id = id;
        this.uri = uri;
        this.isNew = isNew;
    }

    protected Image(Parcel in) {
        id = in.readInt();
        uri = in.readParcelable(Uri.class.getClassLoader());
        isNew = in.readByte() != 0;
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(uri, flags);
        dest.writeByte((byte) (isNew ? 1 : 0));
    }
}
