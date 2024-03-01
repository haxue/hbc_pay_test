package com.hsbc.test.book.lib_db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_book")
data class Book(
    @PrimaryKey
    val isbn: String,
    var title: String,
    var image: String? = null,
    var author: String,
    var publishYear: String,
)