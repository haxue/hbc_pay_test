package com.hsbc.test.book.library.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hsbc.test.book.library.activity.home.MainActivity
import com.hsbc.test.book.library.activity.home.composable.bars.NavBottomBar
import com.hsbc.test.book.library.common.local_provider.BookViewModelProvider
import com.hsbc.test.book.library.ui.theme.HSBC_TEST_BOOK_LIBRARYTheme
import com.hsbc.test.book.library.vm.BookListViewModel
import org.junit.Rule
import org.junit.Test


class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    @get:Rule
    val activityRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun homeScreenTest() {
//        activityRule.setContent {
//            val vm : BookListViewModel = viewModel()
//            HSBC_TEST_BOOK_LIBRARYTheme {
//                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
//                    CompositionLocalProvider(value = BookViewModelProvider provides vm) {
//                        NavBottomBar()
//                    }
//                }
//            }
//        }
        activityRule.onNodeWithText("Home").performClick()
        activityRule
            .onNodeWithText("Home", useUnmergedTree = true).printToLog("Home Button")
        activityRule
            .onNodeWithText("Home", useUnmergedTree = true).assertIsDisplayed()
        activityRule
            .onNodeWithText("NewBook", useUnmergedTree = true).assertIsDisplayed()
    }
}