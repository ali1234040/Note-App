package com.example.noteapp.navigation

sealed class Screens(val route: String) {

    object Home : Screens("home")
    object AddNotes : Screens("add_notes")

    fun routeWithArgs(vararg args: String) =
        buildString{
            append(route)
            args.forEachIndexed{index, _ ->
                append("?$index={$index}")
            }
        }

    fun paramsWithArgs(vararg args: String) =
        buildString{
            append(route)
            args.forEachIndexed{index, value ->
                append("?$index=$value")
            }
        }
}