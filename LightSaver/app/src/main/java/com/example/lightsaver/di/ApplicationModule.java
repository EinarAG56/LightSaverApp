package com.example.lightsaver.di;

import android.app.Application;
import android.content.Context;


import com.example.lightsaver.BaseApplication;
import com.example.lightsaver.persistence.AppDatabase;
import com.example.lightsaver.persistence.MessageDao;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
abstract class ApplicationModule {

    @Binds
    abstract Application application(BaseApplication baseApplication);

    @Provides
    @Singleton
    static Context provideContext(Application application) {
        return application;
    }

    @Singleton
    @Provides
    static AppDatabase provideDatabase(Application app) {
        return AppDatabase.getInstance(app);
    }

    @Singleton
    @Provides
    static MessageDao provideMessageDao(AppDatabase database) {
        return database.messageDao();
    }
}