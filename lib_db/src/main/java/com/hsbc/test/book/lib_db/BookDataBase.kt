package com.hsbc.test.book.lib_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hsbc.test.book.lib_db.dao.book.BookDao
import com.hsbc.test.book.lib_db.dao.user.UserDao
import com.hsbc.test.book.lib_db.entity.Book
import com.hsbc.test.book.lib_db.entity.User

@Database(
    entities = [User::class, Book::class],
    version = 1,
    exportSchema = false
)
abstract class BookDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun bookDao(): BookDao

    companion object {
        @Volatile
        private var INSTANCE: BookDataBase? = null
        fun getDataBase(app: Context? = null, firstTimeInitCallback: ((BookDataBase) -> Unit)? = null): BookDataBase {
            val db = INSTANCE
            if (db != null) {
                return db
            } else {
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        app!!,
                        BookDataBase::class.java,
                        "db_book"
                    )
                        .build()
                    INSTANCE = instance
                    firstTimeInitCallback?.invoke(instance)
                    return instance
                }
            }
        }
    }
}