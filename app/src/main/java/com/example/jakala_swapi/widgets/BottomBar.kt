package com.example.jakala_swapi.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jakala_swapi.R
import com.example.jakala_swapi.navigation.NavigationItem

@Composable
fun BottomBar(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>,
) {
    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            val screens = NavigationItem.BottomNav.bottomNavDestinations()
            NavigationBar(containerColor = Color.White) {
                val backStackEntry = navController.currentBackStackEntryAsState()
                val currentRoute = backStackEntry.value?.destination?.route
                screens.forEach { screen ->
                    val selected = currentRoute == screen.route
                    NavigationBarItem(
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(NavigationItem.BottomNav.Movies.route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            val iconId = if (selected) (screen.selectedIcon) else screen.icon
                            Image(
                                painterResource(id = iconId),
//                                bitmap = rememberIconBitmapImage(
//                                    vectorResId = iconId,
//                                    iconSize = 24.dp
//                                )!!,
                                contentDescription = "${screen.name}",
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(screen.name),
                                color = colorResource(id = R.color.black),
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 12.sp
                            )
                        },
                        alwaysShowLabel = true,
                        selected = selected,
                        colors = NavigationBarItemDefaults
                            .colors(
//                                indicatorColor = colorResource(id = de.weloveapps.wanderwege.core.ui.R.color.app_blue_25)
                            )
                    )
                }
            }
        })
}
