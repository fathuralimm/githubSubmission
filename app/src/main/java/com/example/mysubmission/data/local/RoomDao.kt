package com.example.mysubmission.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mysubmission.data.model.ItemsItem


@Dao
interface RoomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: ItemsItem)

    @Query("SELECT * FROM users")
    fun loadAll(): LiveData<MutableList<ItemsItem>>

    @Query("SELECT * FROM users WHERE id LIKE :id LIMIT 1")
    fun findById(id: Int): ItemsItem

    @Delete
    fun delete(user: ItemsItem)
}
