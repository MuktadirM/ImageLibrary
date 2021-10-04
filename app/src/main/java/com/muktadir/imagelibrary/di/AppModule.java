package com.muktadir.imagelibrary.di;

import static com.muktadir.imagelibrary.utils.Constrains.DATABASE_NAME;

import android.app.Application;

import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.muktadir.imagelibrary.R;
import com.muktadir.imagelibrary.repository.local.LocalDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Singleton
    @Provides
    static RequestOptions provideRequestOptions(){
        return RequestOptions.placeholderOf(R.drawable.ic_baseline_cloud_download_100)
                .error(R.drawable.ic_baseline_error_100);
    }

    @Singleton
    @Provides
    static RequestManager provideGlideInstance(Application application, RequestOptions requestOptions){
        return Glide.with(application)
                .setDefaultRequestOptions(requestOptions);
    }

    @Singleton
    @Provides
    static LocalDatabase provideLocalDatabase(Application application){
        return Room.databaseBuilder(application,LocalDatabase.class,DATABASE_NAME).build();
    }

}
