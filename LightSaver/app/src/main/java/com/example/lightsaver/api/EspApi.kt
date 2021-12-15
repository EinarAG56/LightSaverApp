package com.example.lightsaver.api

import com.google.gson.GsonBuilder
import com.example.lightsaver.api.adapters.DataTypeAdapterFactory
import io.reactivex.Observable

import okhttp3.OkHttpClient

import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class EspApi(private val address: String) {

    private val service: EspService

    init {

        val base_url = "http://$address/"
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addNetworkInterceptor(logging)
                .build()

        val gson = GsonBuilder()
                .setLenient()
                .registerTypeAdapterFactory(DataTypeAdapterFactory())
                .create()

        val retrofit = Retrofit.Builder()
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(base_url)
                .build()

        service = retrofit.create(EspService::class.java)
    }

    fun sendState(state:String): Observable<MessageResponse> {
        return service.sendState(state)
    }
}