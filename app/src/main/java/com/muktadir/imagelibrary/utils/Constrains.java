package com.muktadir.imagelibrary.utils;

import java.util.Date;

public class Constrains {
    public static String EDIT_IMAGE = "EditImage";
    public static String DATABASE_NAME = "image_library.db";
    public static final int DB_VERSION = 1;


    public static String humanDiff(Date dateTime){
        return dateTime.toString();
    }
}
