package com.example.lightsaver.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.lightsaver.BaseApplication;
import com.example.lightsaver.di.AndroidBindingModule;
import com.example.lightsaver.di.DaggerViewModelInjectionModule;
import com.example.lightsaver.persistence.Preferences;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.Provides;
import dagger.Subcomponent;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ApplicationModule.class,
        ActivityModule.class,
        AndroidBindingModule.class,
        DaggerViewModelInjectionModule.class
})

@ApplicationScope
public interface ApplicationComponent extends AndroidInjector<BaseApplication> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<BaseApplication>{}

}