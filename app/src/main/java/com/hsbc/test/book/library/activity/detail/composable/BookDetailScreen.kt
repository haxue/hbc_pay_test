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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.hsbc.test.book.library.common.constant.ActionStatus
import com.hsbc.test.book.library.common.constant.LoadingStatus
import com.hsbc.test.book.library.data.vo.BookVO
import com.hsbc.test.book.library.vm.BookDetailViewModel

private const val TAG = "BookDetailScreen"
@Composable
fun BookDetailScreen(lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current, id: String, vm: BookDetailViewModel) {
    val (showEdit, setShowEdit) = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        if (id.isNotBlank()) {
            vm.fetchBookDetail(id)
        }
    }
    val detail by vm.bookDetail.collectAsStateWithLifecycle()
    val loadingStatus by vm.loadingStatue.collectAsStateWithLifecycle()
    val deleteState by vm.deleteState.collectAsStateWithLifecycle()
    val editState by vm.editState.collectAsStateWithLifecycle()
    if (editState == ActionStatus.Done || editState == ActionStatus.Error) {
        setShowEdit(false)
    }
    detail?.let { vo ->
        Box(modifier = Modifier.fillMaxSize()) {
            DetailContent(vo, vm, setShowEdit)
            if (deleteState == ActionStatus.Doing) {
                DeleteDoingView()
            } else if (deleteState == ActionStatus.Done) {
                DeleteDoneView()
            }
        }
        if (showEdit) {
            BookDetailEdit(book = vo, editState) { toSave, updateVO ->
                if (toSave) {
                    // start to save book
                    vm.saveBook(updateVO!!)
                } else {
                    setShowEdit(false)
                }
            }
        }
    }
    if (loadingStatus == LoadingStatus.Loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Text(text = "loading...", modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
private fun DeleteDoneView() {
    Box(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
    ) {
        Text(
            text = "success to delete the book",
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontWeight = FontWeight(800)
        )
    }

}

@Composable
private fun DeleteDoingView() {
    Box(
        modifier = Modifier
            .background(color = Color(0xdd888888))
            .fillMaxSize()
    ) {
        Text(
            text = "deleting the book...",
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontWeight = FontWeight(800)
        )
    }
}

@Composable
private fun DetailContent(vo: BookVO, vm: BookDetailViewModel, setEditStatus: (Boolean) -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        AsyncImage(
            model = vo.image,
            contentDescription = "book: ${vo.title}",
            modifier = Modifier
                .width(200.dp)
                .height(200.dp)
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(text = "Title: ${vo.title}", fontWeight = FontWeight(800), fontSize = 16.sp)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "Author: ${vo.author}")
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "Date: ${vo.publishYear}")
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "ISBN: ${vo.isbn}")
        Spacer(modifier = Modifier.weight(1f))
        Row(
            horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier
                .padding(top = 20.dp, bottom = 20.dp)
                .fillMaxWidth()
        ) {
            Button(
                onClick = {
                    vm.deleteBook(vo.isbn)
                }, modifier = Modifier
                    .width(148.dp)
                    .height(48.dp)
            ) {
                Text(text = "Delete")
            }

            Button(
                onClick = {
                    vm.resetEditStatus()
                    setEditStatus(true)
                }, modifier = Modifier
                    .width(148.dp)
                    .height(48.dp)
            ) {
                Text(text = "Edit")
            }
        }
    }
}