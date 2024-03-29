package com.hsbc.test.book.library.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hsbc.test.book.library.common.constant.ActionStatus
import com.hsbc.test.book.library.common.constant.LoadingStatus
import com.hsbc.test.book.library.common.utils.bookTransformToVO
import com.hsbc.test.book.library.common.utils.bookVOToBook
import com.hsbc.test.book.library.data.repository.BookRepository
import com.hsbc.test.book.library.data.vo.BookVO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor() : ViewModel() {
    private val _bookDetail = MutableStateFlow<BookVO?>(null)
    private val _loadingState = MutableStateFlow(LoadingStatus.Default)
    private val _deleteState = MutableStateFlow(ActionStatus.Default)
    private val _editState = MutableStateFlow(ActionStatus.Default)

    val loadingStatue: StateFlow<LoadingStatus> = _loadingState.asStateFlow()
    val bookDetail: StateFlow<BookVO?> = _bookDetail.asStateFlow()
    val deleteState: StateFlow<ActionStatus> = _deleteState.asStateFlow()
    val editState: StateFlow<ActionStatus> = _editState.asStateFlow()

    @Inject
    lateinit var repository: BookRepository

    fun fetchBookDetail(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.value = LoadingStatus.Loading
            delay(500)
            val book = repository.fetchBook(id)
            if (book != null) {
                _bookDetail.value = book.bookTransformToVO()
            }

            _loadingState.value = LoadingStatus.Done
        }
    }

    fun deleteBook(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _deleteState.value = ActionStatus.Doing
            try {
                delay(500)
                val delNum = repository.removeBook(id)
                if (delNum >= 1) {
                    // delete book successfully
                    _deleteState.value = ActionStatus.Done
                    repository.notifyDeleteBook(id)
                } else {
                    _deleteState.value = ActionStatus.Error
                    repository.notifyDeleteBook(null)
                }
            } catch (ex: Exception) {
                _deleteState.value = ActionStatus.Error
            }

        }
    }

    fun saveBook(updateVO: BookVO) {
        viewModelScope.launch(Dispatchers.IO) {
            _editState.value = ActionStatus.Doing
            try {
                delay(500)
                val updateBook = updateVO.bookVOToBook()
                val updateNum = repository.saveBook(updateBook)
                if (updateNum >= 1) {
                    repository.notifyBookUpdated(updateBook)
                    _editState.value = ActionStatus.Done
                    fetchBookDetail(updateBook.isbn)
                } else {
                    _editState.value = ActionStatus.Error
                }
            } catch (ex: Exception) {
                _editState.value = ActionStatus.Error
            }
        }
    }

    fun resetEditStatus() {
        _editState.value = ActionStatus.Default
    }

}