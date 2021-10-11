package com.muktadir.imagelibrary.utils;

public interface ItemOnClick<T>{
    public void ItemClicked(T item,int position);
    public void ShareImage(T item);
    public void DeleteImage(T item,int position);
}
