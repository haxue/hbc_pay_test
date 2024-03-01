package com.hsbc.test.book.library.activity.home.composable.newbook

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hsbc.test.book.library.common.constant.ActionStatus
import com.hsbc.test.book.library.common.local_provider.BookViewModelProvider
import com.hsbc.test.book.library.data.vo.BookVO
import java.util.UUID

@ExperimentalMaterial3Api
@Composable
fun NewBookScreen() {
    val vm = BookViewModelProvider.current
    val insertState by vm.insertBookState.collectAsStateWithLifecycle()
    DisposableEffect(null) {
        onDispose {
            vm.resetInsertState()
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        EditView {
            vm.addNewBook(it)
        }
        AddBookStatusView(insertState)
    }
}

@Composable
fun AddBookStatusView(insertState: ActionStatus) {
    if (insertState != ActionStatus.Default) {
        val text = when (insertState) {
            ActionStatus.Doing -> {
                "saving the book..."
            }

            ActionStatus.Error -> {
                "failed to add new book..."
            }

            ActionStatus.Done -> {
                "success to add new book"
            }

            else -> {
                ""
            }
        }
        Box(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxSize()
        ) {
            Text(
                text = text,
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight(800)
            )
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun EditView(insertCallback: (BookVO) -> Unit) {
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    val labelWidth = 100.dp
    Column {
        Text(text = "Add new book", fontWeight = FontWeight(800), modifier = Modifier.padding(8.dp))
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Title:", modifier = Modifier.width(labelWidth))
            OutlinedTextField(value = title, onValueChange = {
                title = it
            })
        }
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Author:", modifier = Modifier.width(labelWidth))
            OutlinedTextField(value = author, onValueChange = {
                author = it
            })
        }
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Year", modifier = Modifier.width(labelWidth))
            OutlinedTextField(value = year, onValueChange = {
                year = it
            })
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                if (title.isNotBlank() && author.isNotBlank() && year.isNotBlank()) {
                    val newBook = BookVO(title = title, author = author, publishYear = year, isbn = "ISBN_${UUID.randomUUID()}")
                    insertCallback(newBook)
                }
            }, modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(150.dp)
        ) {
            Text(text = "Insert")
        }
    }
}