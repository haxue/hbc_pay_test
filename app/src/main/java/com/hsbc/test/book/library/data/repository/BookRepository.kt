package com.hsbc.test.book.library.data.repository

import com.hsbc.test.book.lib_db.BookDataBase
import com.hsbc.test.book.lib_db.entity.Book
import kotlinx.coroutines.flow.MutableStateFlow

object BookRepository {
    val deleteEvent = MutableStateFlow<String?>(null)
    val updateEvent = MutableStateFlow<Book?>(null)
    fun fetchAllBook(dataBase: BookDataBase = BookDataBase.getDataBase()): List<Book> {
        val bookDao = dataBase.bookDao()
        return bookDao.getAllBook()
    }

    fun fetchBook(id: String, dataBase: BookDataBase = BookDataBase.getDataBase()): Book? {
        val bookDao = dataBase.bookDao()
        return bookDao.getBook(id)
    }

    fun removeBook(id: String, dataBase: BookDataBase = BookDataBase.getDataBase()): Int {
        val bookDao = dataBase.bookDao()
        return bookDao.deleteBook(id)
    }

    fun saveBook(book: Book, dataBase: BookDataBase = BookDataBase.getDataBase()): Int {
        val bookDao = dataBase.bookDao()
        return bookDao.update(book)
    }

    fun insertBook(book: Book, dataBase: BookDataBase = BookDataBase.getDataBase()) {
        val bookDao = dataBase.bookDao()
        bookDao.insert(book)
    }

    fun notifyDeleteBook(id: String?) {
        deleteEvent.value = id
    }

    suspend fun notifyBookUpdated(updateBook: Book) {
        updateEvent.emit(updateBook)
    }
}