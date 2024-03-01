package com.hsbc.test.book.library.common.local_provider

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import com.hsbc.test.book.library.vm.BookListViewModel
import com.hsbc.test.book.library.vm.BookViewModel

val AppNavController = compositionLocalOf<NavHostController>() { error("NavHostController error") }
val BookViewModelProvider = compositionLocalOf<BookListViewModel>() { error("BookViewModelProvider error") }