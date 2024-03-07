package com.hsbc.test.book.library.hilt

import com.hsbc.test.book.library.data.repository.BookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltBookRepository {
    @Provides
    @Singleton
    fun provideBookRepository(): BookRepository {
        return BookRepository
    }
}