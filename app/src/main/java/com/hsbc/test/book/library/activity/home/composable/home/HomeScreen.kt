package com.hsbc.test.book.library.activity.home.composable.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import com.hsbc.test.book.library.activity.home.composable.home.book_list.BookList

@Composable
fun HomeScreen() {
    //todo add action bar here
    BookList()
}