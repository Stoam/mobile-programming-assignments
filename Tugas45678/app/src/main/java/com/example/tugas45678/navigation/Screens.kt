package com.example.tugas45678.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Update
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(
    val title: String,
    val route: String,
    val icon: ImageVector,
) {
    data object Create: Screens(
        title = "Create",
        route = "create",
        icon = Icons.Filled.AddCircle,
    )
    data object Read: Screens(
        title = "Read",
        route = "read",
        icon = Icons.Filled.Person,
    )
    data object AllMhs: Screens(
        title = "All",
        route = "all",
        icon = Icons.Filled.People
    )
    data object Update: Screens(
        title = "Update",
        route = "update",
        icon = Icons.Filled.Update,
    )
    data object Delete: Screens(
        title = "Delete",
        route = "delete",
        icon = Icons.Filled.Delete,
    )
}