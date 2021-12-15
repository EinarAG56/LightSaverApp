package com.example.lightsaver.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lightsaver.vo.Message
import io.reactivex.Flowable

/**
 * Data Access Object for the messages table.
 */
@Dao
interface MessageDao {

    /**
     * Get all messages
     * @return list of all messages
     */
    @Query("SELECT * FROM Messages")
    fun getMessages(): Flowable<List<Message>>

    /**
     * Insert a message in the database. If the message already exists, replace it.
     * @param user the message to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(message: Message)

    /**
     * Delete all messages.
     */
    @Query("DELETE FROM Messages")
    fun deleteAllMessages()
}