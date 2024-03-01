package com.hsbc.test.book.library.activity.home.composable.home.book_list

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.hsbc.test.book.library.activity.detail.BookDetailActivity
import com.hsbc.test.book.library.common.constant.LoadingStatus
import com.hsbc.test.book.library.common.local_provider.BookViewModelProvider
import com.hsbc.test.book.library.common.route.DETAIL_ARGUMENT_ID
import com.hsbc.test.book.library.data.vo.BookVO
import java.util.Locale

private const val TAG = "BookList"

@Composable
fun BookList() {
//    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val vm = BookViewModelProvider.current
    val bookList by vm.bookListState.collectAsStateWithLifecycle()
    val loadingStatus by vm.loadingStatue.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        vm.fetchBookList()
    }
    if (bookList.isNotEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(state = listState) {
                items(bookList.size, key = { index -> bookList[index].isbn }) { index ->
                    Column {
                        BookItem(bookList[index])
                        Divider(
                            color = Color.LightGray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }
            }
        }
    } else if (loadingStatus == LoadingStatus.Loading) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(text = "loading...", modifier = Modifier.align(Alignment.Center))
        }
    } else if (loadingStatus == LoadingStatus.Done) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Empty book list, please click [NewBook] to add!",
                fontWeight = FontWeight(800),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )
        }
    }

}

@Composable
private fun BookItem(vo: BookVO) {
    val context = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                val intent = Intent(context, BookDetailActivity::class.java)
                intent.putExtra(DETAIL_ARGUMENT_ID, vo.isbn)
                context.startActivity(intent)
            }
    ) {
        AsyncImage(
            model = vo.image,
            contentDescription = "book: ${vo.title}",
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "${vo.title.capitalize(Locale.CHINA)}", fontSize = 16.sp, fontWeight = FontWeight(800), overflow = TextOverflow.Ellipsis)
            Text(text = "${vo.author}", fontSize = 14.sp, color = Color.DarkGray, overflow = TextOverflow.Ellipsis)
            Text(text = "${vo.publishYear}", fontSize = 14.sp, color = Color.DarkGray)
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.Bottom)
        ) {
            Text(
                text = "Detail>>",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp)
            )
        }
    }
}