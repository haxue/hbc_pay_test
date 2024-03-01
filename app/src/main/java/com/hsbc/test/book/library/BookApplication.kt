package com.hsbc.test.book.library

import android.app.Application
import com.hsbc.test.book.lib_db.BookDataBase
import com.hsbc.test.book.lib_db.entity.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BookApplication : Application() {
    companion object {
        private const val TAG = "BookApplication"
    }

    override fun onCreate() {
        super.onCreate()
        val curTime = System.currentTimeMillis()
        BookDataBase.getDataBase(this) {
            GlobalScope.launch(Dispatchers.Default) {
                insertDemoData(it)
            }
        }
    }

    /**
     * 插入测试数据以作演示用，仅用开发过程
     */
    private fun insertDemoData(it: BookDataBase) {
        val userDao = it.bookDao()
        val book1 = Book(
            title = "Python编程从入门到实践 第3版",
            author = "埃里克·马瑟斯",
            image = "https://img3m9.ddimg.cn/23/13/29564789-1_u_6.jpg",
            publishYear = "2023",
            isbn = "ISBN_PYH"
        )
        val book2 = Book(
            title = "SQL数据分析实战（第2版）",
            author = "马特·古德瓦瑟",
            publishYear = "2022",
            image = "https://img3m5.ddimg.cn/74/36/29508905-1_u_3.jpg",
            isbn = "ISBN_SQL"
        )
        val book3 = Book(
            title = "C Primer Plus 第6版 中文版 C语言入门经典教程",
            author = "史蒂芬·普拉达",
            publishYear = "2019",
            image = "https://img3m8.ddimg.cn/74/7/28518608-1_w_16.jpg",
            isbn = "ISBN_CPP"
        )
        val book4 = Book(
            title = "Web前端开发 HTML5+CSS3+JavaScript+Vue.js+jQuery",
            author = "刘兵",
            publishYear = "2020",
            image = "https://img3m3.ddimg.cn/80/4/29116673-1_w_7.jpg",
            isbn = "ISBN_WEB"
        )
        val book5 = Book(
            title = "写给青少年的人工智能（Python版）",
            author = "陈?Z、王萌、梁婷",
            publishYear = "2023",
            image = "https://img3m9.ddimg.cn/14/17/29553989-1_w_2.jpg",
            isbn = "ISBN_EDU_PYTHON"
        )
        val book6 = Book(
            title = "Scratch编程从入门到精通（第2版）",
            author = "谢声涛",
            publishYear = "2023",
            image = "https://img3m0.ddimg.cn/37/19/29602720-1_w_1690193225.jpg",
            isbn = "ISBN_EDU_SCRATCH"
        )
        userDao.insertOrUpdate(listOf(book1, book2, book3, book4, book5, book6))
    }
}