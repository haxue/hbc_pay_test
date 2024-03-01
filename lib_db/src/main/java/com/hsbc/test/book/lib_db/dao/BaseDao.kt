package com.hsbc.test.book.lib_db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
interface BaseDao<T> {
    @Insert
    fun insert(bean: T)

    @Insert
    fun insertAll(list: List<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(list: List<T>)

    @Delete
    fun delete(bean: T)

    @Delete
    fun deleteAll(list: List<T>)

    @Update
    fun update(bean: T): Int
}