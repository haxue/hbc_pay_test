package com.hsbc.test.book.library.data.repository

import android.util.Log
import com.hsbc.test.book.lib_db.BookDataBase
import com.hsbc.test.book.lib_db.entity.User
import kotlin.random.Random

object UserRepository {
    private const val TAG = "UserRepository"
    fun fetchAllUser() {
        val userDao = BookDataBase.getDataBase().userDao()
        val userList = userDao.getAllUser()
        Log.e(TAG, userList.joinToString())
    }
}