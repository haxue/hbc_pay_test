package com.hsbc.test.book.library.activity.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.hsbc.test.book.library.activity.detail.composable.BookDetailScreen
import com.hsbc.test.book.library.common.route.DETAIL_ARGUMENT_ID
import com.hsbc.test.book.library.vm.BookDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookDetailActivity: ComponentActivity() {
    companion object {
        private const val TAG = "BookDetailActivity"
    }
    private val vm by viewModels<BookDetailViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getStringExtra(DETAIL_ARGUMENT_ID) ?: ""
        setContent {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                BookDetailScreen(id = id, vm = vm)
            }
        }
    }
}