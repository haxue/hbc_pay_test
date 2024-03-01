package com.hsbc.test.book.library.activity.home.composable.bars

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hsbc.test.book.library.activity.home.composable.home.HomeScreen
import com.hsbc.test.book.library.activity.home.composable.newbook.NewBookScreen
import com.hsbc.test.book.library.activity.home.composable.search.SearchScreen
import com.hsbc.test.book.library.common.local_provider.AppNavController
import com.hsbc.test.book.library.common.route.RouteList
import com.hsbc.test.book.library.common.route.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBottomBar() {
    val navController = rememberNavController()
    Column(modifier = Modifier.fillMaxSize()) {
        CompositionLocalProvider(value = AppNavController provides navController) {
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1F)
            ) {
                composable(Screen.Home.route) { HomeScreen() }
                composable(Screen.NewBook.route) { NewBookScreen() }
                composable(Screen.Search.route) { SearchScreen() }
//                composable(Screen.Detail.route, arguments = listOf(
//                    navArgument(DETAIL_ARGUMENT_ID) {
//                        type = NavType.StringType
//                        defaultValue = ""
//                    }
//                )) { BookDetailScreen() }
            }
        }
        val bottomBarHeight = 60.dp
        BottomBar(
            controller = navController, items = RouteList, modifier = Modifier
                .fillMaxWidth()
                .height(bottomBarHeight)
        )
    }
}

@Composable
fun BottomBar(controller: NavHostController, items: List<Screen>, modifier: Modifier = Modifier) {
    val backstackEntry by controller.currentBackStackEntryAsState()
    val curDestination = backstackEntry?.destination
    Row(
        modifier = modifier.background(Color.White),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed { _, screen ->
            BarItem(item = screen,
                isSelected = curDestination?.hierarchy?.any { it.route == screen.route },
                onItemClicked = {
                    controller.popBackStack()
                    controller.navigate(screen.route) {
                        popUpTo(controller.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
    }

}

@Composable
private fun BarItem(
    item: Screen,
    isSelected: Boolean?,   //是否选中
    onItemClicked: () -> Unit,  //按钮点击监听
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickableWithoutInteraction { onItemClicked.invoke() }
            .padding(vertical = 8.dp)) {
        Icon(
            modifier = Modifier.size(30.dp),
            imageVector = item.icon,
            contentDescription = item.title,
            tint = if (isSelected == true) Color.Blue else Color.Gray
        )
        Text(
            text = item.title,
            color = if (isSelected == true) Color.Blue else Color.Gray,
            fontSize = 10.sp,
        )
    }
}

inline fun Modifier.clickableWithoutInteraction(crossinline onClick: () -> Unit): Modifier =
    this.composed {
        clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) {
            onClick()
        }
    }