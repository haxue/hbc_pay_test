package com.hsbc.test.book.lib_db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.hsbc.test.book.lib_db.dao.book.BookDao
import com.hsbc.test.book.lib_db.entity.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.UUID
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4::class)
@SmallTest
class BookDaoTest {
    private lateinit var database: BookDataBase
    private lateinit var bookDao: BookDao

    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            BookDataBase::class.java
        ).allowMainThreadQueries().build()

        bookDao = database.bookDao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun getAllBook_test() = runBlocking {
        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            try {
                bookDao.insert(
                    Book(
                        isbn = "ISBN_${UUID.randomUUID()}",
                        title = "title",
                        author = "author",
                        publishYear = "2023"
                    )
                )
                val bookList = bookDao.getAllBook()
                assert(bookList.isNotEmpty()) {
                    "Could not find any book entity"
                }
            } catch (ex: Exception) {
                assert(false)
            } finally {
                latch.countDown()
            }

        }
        latch.await()
        job.cancelAndJoin()
    }

    @Test
    fun getBook_test() = runBlocking {
        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            try {
                val isbn = "ISBN_12345678"
                val insertBook = Book(
                    isbn = isbn,
                    title = "title",
                    author = "author",
                    publishYear = "2023"
                )
                bookDao.insert(insertBook)
                val book = bookDao.getBook(isbn)
                assert(book?.isbn == insertBook.isbn) {
                    println("same isbn")
                }
                assert(book?.title == insertBook.title)
                assert(book?.author == insertBook.author)
                assert(book?.publishYear == insertBook.publishYear)
                assert(book?.image == insertBook.image)
            } catch (ex: Exception) {
                assert(false)
            } finally {
                latch.countDown()
            }
        }
        latch.await()
        job.cancelAndJoin()
    }

    @Test
    fun deleteBook_test() = runBlocking {
        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            try {
                val isbn = "ISBN_12345678"
                val insertBook = Book(
                    isbn = isbn,
                    title = "title",
                    author = "author",
                    publishYear = "2023"
                )
                bookDao.insert(insertBook)
                val book = bookDao.getBook(isbn)
                assert(book != null) {
                    "Could not find book based on the given isbn: $isbn"
                }
                bookDao.deleteBook(book!!.isbn)
                val delBook = bookDao.getBook(isbn)
                assert(delBook == null)
            } catch (ex: Exception) {
                assert(false)
            } finally {
                latch.countDown()
            }
        }
        latch.await()
        job.cancelAndJoin()
    }

    @Test
    fun update_test() = runBlocking {
        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            try {
                val isbn = "ISBN_12345678"
                val insertBook = Book(
                    isbn = isbn,
                    title = "title",
                    author = "author",
                    publishYear = "2023"
                )
                bookDao.insert(insertBook)
                val book = bookDao.getBook(isbn)
                assert(book != null) {
                    "Could not find book based on the given isbn: $isbn"
                }
                book!!.title = "new title"
                book.author = "new author"
                book.publishYear = "new publishYear"
                val affectedNum = bookDao.update(book)
                assert(affectedNum == 1)
            } catch (ex: Exception) {
                assert(false)
            } finally {
                latch.countDown()
            }
        }
        latch.await()
        job.cancelAndJoin()
    }
}