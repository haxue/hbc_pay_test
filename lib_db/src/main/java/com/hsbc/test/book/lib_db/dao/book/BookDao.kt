package com.hsbc.test.book.lib_db.dao.book

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.hsbc.test.book.lib_db.dao.BaseDao
import com.hsbc.test.book.lib_db.entity.Book

@Dao
interface BookDao: BaseDao<Book> {

    @Query("select * from tb_book")
    fun getAllBook(): List<Book>

    @Query("select * from tb_book where isbn == :id")
    fun getBook(id: String): Book?

    @Query("delete from tb_book where isbn == :id")
    fun deleteBook(id: String): Int
}