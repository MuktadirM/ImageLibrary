package com.muktadir.imagelibrary.di;

import androidx.lifecycle.ViewModelProvider;

import com.muktadir.imagelibrary.viewModels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelProviderFactory(ViewModelProviderFactory modelProviderFactory);
}
