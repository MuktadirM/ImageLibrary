package com.muktadir.imagelibrary.di.domain;

import com.muktadir.imagelibrary.domain.services.IImageServices;
import com.muktadir.imagelibrary.repository.ImageServices;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class DomainCoreModules {

    @Binds
    @Singleton
    abstract IImageServices providesImageServices(ImageServices imageServices);

}
