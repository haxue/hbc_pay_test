package com.hsbc.test.book.library.common.utils

import com.hsbc.test.book.lib_db.entity.Book
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BookConverterHelperTest {

    @Test
    fun bookTransformToVO_test() {
        val isbn = "ISBN_12345678"
        val book = Book(
            isbn = isbn,
            title = "title",
            author = "author",
            publishYear = "2023"
        )
        val bookVO = book.bookTransformToVO()
        assert(book.isbn == bookVO.isbn)
        assert(book.title == bookVO.title)
        assert(book.author == bookVO.author)
        assert(book.publishYear == bookVO.publishYear)
        assert(book.image == bookVO.image)
    }
}