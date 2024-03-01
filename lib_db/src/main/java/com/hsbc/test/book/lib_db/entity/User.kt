package com.hsbc.test.book.lib_db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_user")
data class User(

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "userNum")
    val userNum: String,
    @ColumnInfo(name = "userPwd")
    val userPwd: String? = null,
    @ColumnInfo(name = "sex")
    val sex: String? = null,
    @ColumnInfo(name = "age")
    val age: Int,
    @ColumnInfo(name = "school")
    val school: String? = null,
)
