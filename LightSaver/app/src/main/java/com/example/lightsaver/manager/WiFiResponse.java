package com.example.lightsaver.manager;


import javax.annotation.Nullable;

import static com.example.lightsaver.manager.WiFiStatus.CONNECTED;
import static com.example.lightsaver.manager.WiFiStatus.CONNECTING;
import static com.example.lightsaver.manager.WiFiStatus.DISCONNECTED;
import static com.example.lightsaver.manager.WiFiStatus.DISCONNECTING;
import static com.example.lightsaver.manager.WiFiStatus.ERROR;

import androidx.annotation.NonNull;


/**
 * Response holder provided to the UI
 * https://proandroiddev.com/mvvm-architecture-using-livedata-rxjava-and-new-dagger-android-injection-639837b1eb6c
 */
public class WiFiResponse {

    public WiFiStatus status;

    @Nullable
    public final String data;

    @Nullable
    public final Throwable error;

    private WiFiResponse(WiFiStatus status, @Nullable String data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static WiFiResponse connecting() {
        return new WiFiResponse(CONNECTING, null, null);
    }

    public static WiFiResponse connected() {
        return new WiFiResponse(CONNECTED, null, null);
    }

    public static WiFiResponse disconnected() {
        return new WiFiResponse(DISCONNECTED, null, null);
    }

    public static WiFiResponse disconnecting() {
        return new WiFiResponse(DISCONNECTING, null, null);
    }

    public static WiFiResponse error(@NonNull Throwable error) {
        return new WiFiResponse(ERROR, null, error);
    }
}