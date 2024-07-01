package com.example.tugas45678

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.tugas45678.navigation.BottomBar
import com.example.tugas45678.navigation.NavigationGraph
import com.example.tugas45678.navigation.TopBar
import com.example.tugas45678.ui.theme.CRUD_MhsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CRUD_MhsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp()
                }
            }
        }
    }
}

@Composable
fun MainApp() {
    val navHostController = rememberNavController()
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomBar(navHostController = navHostController) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {paddingValues ->
        NavigationGraph(navHostController = navHostController, paddingValues = paddingValues, snackbarHostState = snackbarHostState)
    }
}