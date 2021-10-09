package com.muktadir.imagelibrary.di.repository;

import com.muktadir.imagelibrary.repository.local.dto.EditedImageDto;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DtoModule {
    @Singleton
    @Provides
    static EditedImageDto provideEditedImageDts(){
        return new EditedImageDto();
    }

}
