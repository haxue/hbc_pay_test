package com.hsbc.test.book.library.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hsbc.test.book.library.common.constant.ActionStatus
import com.hsbc.test.book.library.common.constant.LoadingStatus
import com.hsbc.test.book.library.common.utils.bookTransformToVO
import com.hsbc.test.book.library.common.utils.bookVOToBook
import com.hsbc.test.book.library.data.repository.BookRepository
import com.hsbc.test.book.library.data.vo.BookVO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BookListViewModel(private val repository: BookRepository = BookRepository) : ViewModel() {
    private var _bookListState = MutableStateFlow(emptyList<BookVO>())
    private val _loadingState = MutableStateFlow(LoadingStatus.Default)

    var insertBookState = MutableStateFlow(ActionStatus.Default)
        private set
    val bookListState: StateFlow<List<BookVO>> = _bookListState.asStateFlow()
    val loadingStatue: StateFlow<LoadingStatus> = _loadingState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.deleteEvent.collectLatest { id ->
                if (!id.isNullOrBlank()) {
                    // some book has been delete, need to delete the removed book form book list
                    val newList = mutableListOf<BookVO>().apply {
                        addAll(_bookListState.value)
                    }
                    newList.removeIf { it.isbn == id }
                    _bookListState.emit(newList)
                }
            }
        }
        viewModelScope.launch {
            repository.updateEvent.collectLatest { book ->
                book?.let {
                    val newList = mutableListOf<BookVO>()
                    _bookListState.value.forEach {
                        newList.add(it.copy())
                    }
                    val updateVO = newList.firstOrNull { it.isbn == book.isbn }
                    updateVO?.let {
                        it.title = book.title
                        it.author = book.author
                        it.publishYear = book.publishYear
                        _bookListState.emit(newList)
                    }
                }
            }
        }
    }

    fun fetchBookList(): Job {
        val job = viewModelScope.launch(Dispatchers.IO) {
            _loadingState.value = LoadingStatus.Loading
            val bookList = repository.fetchAllBook()
            _bookListState.value = bookList.map {
                val book = it.bookTransformToVO()
                book
            }
            _loadingState.value = LoadingStatus.Done
        }
        return job
    }

    fun addNewBook(bookVO: BookVO) {
        insertBookState.value = ActionStatus.Doing
        viewModelScope.launch(Dispatchers.IO) {
            val newBook = bookVO.bookVOToBook()
            try {
                repository.insertBook(newBook)
                insertBookState.value = ActionStatus.Done
            } catch (ex: Exception) {
                insertBookState.value = ActionStatus.Error
            }
        }
    }

    fun resetInsertState() {
        insertBookState.value = ActionStatus.Default
    }
}