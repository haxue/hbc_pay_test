package com.hsbc.test.book.library.activity.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hsbc.test.book.library.activity.home.composable.bars.NavBottomBar
import com.hsbc.test.book.library.common.local_provider.BookViewModelProvider
import com.hsbc.test.book.library.ui.theme.HSBC_TEST_BOOK_LIBRARYTheme
import com.hsbc.test.book.library.vm.BookListViewModel

class MainActivity : ComponentActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private val vm by viewModels<BookListViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            val vm : BookListViewModel = viewModel()
            HSBC_TEST_BOOK_LIBRARYTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    CompositionLocalProvider(value = BookViewModelProvider provides vm) {
                        NavBottomBar()
                    }
                }
            }
        }
//        subscribeData()
    }

//    private fun subscribeData() {
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                vm.bookListState.collect {
//                    Log.e(TAG, "bookList size: ${it.size}")
//                }
//            }
//        }
//    }
}


@Preview(showBackground = true, widthDp = 480, heightDp = 720)
@Composable
fun GreetingPreview() {
    HSBC_TEST_BOOK_LIBRARYTheme {
        NavBottomBar()
    }
}