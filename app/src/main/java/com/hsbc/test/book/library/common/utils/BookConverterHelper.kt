package com.hsbc.test.book.library.common.utils

import com.hsbc.test.book.lib_db.entity.Book
import com.hsbc.test.book.library.data.vo.BookVO

fun Book.bookTransformToVO(): BookVO {
    return BookVO(
        title = this.title,
        image = this.image,
        isbn = this.isbn,
        author = this.author,
        publishYear = this.publishYear
    )
}

fun BookVO.bookVOToBook(): Book {
    return Book(
        title = this.title,
        image = this.image,
        isbn = this.isbn,
        author = this.author,
        publishYear = this.publishYear
    )
}