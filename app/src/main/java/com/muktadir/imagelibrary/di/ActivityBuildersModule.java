package com.muktadir.imagelibrary.di;

import com.muktadir.imagelibrary.views.MainHostActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = {FragmentsBuildersModule.class, FragmentViewModelModule.class})
    abstract MainHostActivity contributeMainHostActivity();

}
