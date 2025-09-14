package com.example.noteapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.noteapp.ui.screen.AddNoteScreen
import com.example.noteapp.ui.screen.HomeScreen

@Composable
fun SetupNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.Home.route
    ) {
        composable(
            route = Screens.Home.route
        ) {
            HomeScreen(navController = navController)
        }

        composable(
            route = Screens.AddNotes.routeWithArgs("0: id"),
            arguments = listOf(
                navArgument("0") {
                    type = NavType.IntType
                }
            )
        ) {
            it.arguments?.getInt("0")?.let { id ->
                AddNoteScreen(navController = navController, id = id)
            }
        }
    }
}