package com.hsbc.test.book.library.data.vo

data class BookVO(var title: String,
                  var image: String? = null,
                  var author: String,
                  var publishYear: String,
                  var isbn: String)
