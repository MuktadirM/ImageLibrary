package com.muktadir.imagelibrary.di;

import android.app.Application;

import com.muktadir.imagelibrary.BaseApplication;
import com.muktadir.imagelibrary.di.domain.DomainCoreModules;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ActivityBuildersModule.class,
        ViewModelFactoryModule.class,
        AppModule.class,
        DomainCoreModules.class
})
public interface AppComponent extends AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder{

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
