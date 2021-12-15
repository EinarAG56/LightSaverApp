package com.example.lightsaver.di;

import android.app.Application;
import android.content.Context;


import android.content.SharedPreferences;
import android.content.res.Resources;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;

import com.example.lightsaver.persistence.Preferences;
import com.example.lightsaver.util.DialogUtils;


import java.util.Map;
import java.util.Set;

import dagger.Module;
import dagger.Provides;

@Module
class ActivityModule {
    @Provides
    static SharedPreferences provideSharedPreferences(Application application){
        return application.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    }

    @Provides
    static Preferences provideConfiguration(SharedPreferences appPreferences) {
        return new Preferences(appPreferences);
    }
    @Provides
    static Resources providesResources(Application application) {
        return application.getResources();
    }

    @Provides
    static LayoutInflater providesInflater(Application application) {
        return (LayoutInflater) application.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Provides
    static LocationManager provideLocationManager(Application application) {
        return (LocationManager) application.getSystemService(Context.LOCATION_SERVICE);
    }

    /*@Provides
    static AppPreferences provideAppPreferences(Application application) {
        return new AppPreferences(application);
    }


    }*/
}