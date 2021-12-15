package com.example.lightsaver.api


import androidx.lifecycle.LiveData
import com.example.lightsaver.vo.Message
import io.reactivex.Observable

import retrofit2.Call
import retrofit2.http.*

interface EspService {
    @GET("message/{message}")
    fun sendMessage(@Path("message") message:String): LiveData<ApiResponse<Message>>

    @GET("led/{state}")
    fun sendState(@Path("state") state:String): Observable<MessageResponse>
}