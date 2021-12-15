package com.example.lightsaver.api;


import javax.annotation.Nullable;

import static com.example.lightsaver.api.Status.ERROR;
import static com.example.lightsaver.api.Status.LOADING;
import static com.example.lightsaver.api.Status.SUCCESS;

import androidx.annotation.NonNull;


public class NetworkResponse {

    public  Status status;

    @Nullable
    public final String data;

    @Nullable
    public final Throwable error;

    private NetworkResponse(Status status, @Nullable String data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static NetworkResponse loading() {
        return new NetworkResponse(LOADING, null, null);
    }

    public static NetworkResponse success(@NonNull String data) {
        return new NetworkResponse(SUCCESS, data, null);
    }

    public static NetworkResponse error(@NonNull Throwable error) {
        return new NetworkResponse(ERROR, null, error);
    }
}