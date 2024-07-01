package com.example.tugas45678.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tugas45678.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
    ){
        Image(
            painter = painterResource(id = R.drawable.toolbar_background_kt),
            contentDescription = "toolbar_bg",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        TopAppBar(
            title = {
                Text(
                    text = "CRUD_Basic",
                    fontWeight = FontWeight.Bold,
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
        )
    }
}