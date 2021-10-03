package com.muktadir.imagelibrary.di;

import com.muktadir.imagelibrary.views.image.ListImageView;
import com.muktadir.imagelibrary.views.image.edit.EditImageView;
import com.muktadir.imagelibrary.views.home.HomeView;
import com.muktadir.imagelibrary.views.image.ImageView;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentsBuildersModule {

    @ContributesAndroidInjector
    public abstract HomeView contributesHomeView();

    @ContributesAndroidInjector
    public abstract ListImageView contributesListImageView();

    @ContributesAndroidInjector
    public abstract EditImageView contributesEditImageView();

    @ContributesAndroidInjector
    public abstract ImageView contributesImageView();
}
