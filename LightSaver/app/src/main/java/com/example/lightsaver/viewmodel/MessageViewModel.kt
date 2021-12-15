package com.example.lightsaver.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

import com.example.lightsaver.persistence.MessageDao
import com.example.lightsaver.util.DateUtils
import com.example.lightsaver.vo.Message
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class MessageViewModel @Inject
constructor(application: Application, private val dataSource: MessageDao) : AndroidViewModel(application) {

    /**
     * Get the messages.
     * @return a [Flowable] that will emit every time the messages have been updated.
     */
    fun getMessages():Flowable<List<Message>> {
        return dataSource.getMessages()
                .filter {messages -> messages.isNotEmpty()}
    }

    init {
    }

}