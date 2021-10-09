package com.muktadir.imagelibrary.di;

import androidx.lifecycle.ViewModel;

import com.muktadir.imagelibrary.viewModels.ImageViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class FragmentViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ImageViewModel.class)
    public abstract ViewModel bindOrderViewModel(ImageViewModel imageViewModel);

}
