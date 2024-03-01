package com.hsbc.test.book.library.common.route

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    data object Home : Screen("home", "Home", Icons.Outlined.Home)
    data object NewBook : Screen("new book", "NewBook", Icons.Outlined.Add)
    data object Search : Screen("search", "Search", Icons.Outlined.Search)
//    data object Detail : Screen("detail?id={id}", "Detail", Icons.Outlined.Info) {
//        fun passParams(id: String = ""): String {
//            return "detail?id=${id}"
//        }
//    }
}

val RouteList = listOf(
    Screen.Home,
    Screen.NewBook,
//    Screen.Search
)

const val DETAIL_ARGUMENT_ID = "id"