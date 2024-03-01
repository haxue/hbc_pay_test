package com.hsbc.test.book.library.activity.detail.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hsbc.test.book.library.common.constant.ActionStatus
import com.hsbc.test.book.library.data.vo.BookVO

@Composable
fun BookDetailEdit(book: BookVO, editState: ActionStatus, onSaveCallback: (Boolean, BookVO?) -> Unit) {
    val labelWidth = 100.dp
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        EditBookView(labelWidth, book, onSaveCallback)
        if (editState == ActionStatus.Doing) {
            DoingSaveView()
        }
    }
}
@Composable
private fun DoingSaveView() {
    Box(
        modifier = Modifier
            .background(color = Color(0xdd888888))
            .fillMaxSize()
    ) {
        Text(
            text = "saving the book...",
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontWeight = FontWeight(800)
        )
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun EditBookView(
    labelWidth: Dp,
    book: BookVO,
    onSaveCallback: (Boolean, BookVO?) -> Unit,
) {
    var title by remember { mutableStateOf(book.title) }
    var author by remember { mutableStateOf(book.author) }
    var year by remember { mutableStateOf(book.publishYear) }
    Column {
        Text(text = "Edit book", fontWeight = FontWeight(800), modifier = Modifier.padding(8.dp))
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
            OutlinedTextField(value = year,onValueChange = {
                year = it
            })
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {
                    onSaveCallback.invoke(false, null)
                }, modifier = Modifier
                    .width(150.dp)
            ) {
                Text(text = "Cancel")
            }
            Button(
                onClick = {
                    onSaveCallback.invoke(true, BookVO(title = title, author = author, publishYear = year, isbn = book.isbn, image = book.image))
                }, modifier = Modifier
                    .width(150.dp)
            ) {
                Text(text = "Save")
            }
        }

    }
}

@Preview
@Composable
fun PreviewEditDetail() {
    BookDetailEdit(BookVO(title = "test", isbn = "isbn", author = "author", publishYear = "2000"), ActionStatus.Default) { _, _ ->

    }
}