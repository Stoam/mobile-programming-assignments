package com.example.tugas2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tugas2.ui.theme.Orange_kt
import com.example.tugas2.ui.theme.Purple_kt
import com.example.tugas2.ui.theme.Tugas2Theme
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tugas2Theme {
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

@Preview(showBackground = true)
@Composable
fun MainApp() {
    Scaffold(topBar = { AppBar()}) { values ->
        Calculator(values)
    }
}

@Composable
fun Calculator(values: PaddingValues){
    var equation by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(values)
            .padding(top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Insert Mathematical Equation",
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        OutlinedTextField(
            value = equation,
            onValueChange = { equation = it },
            label = { Text(text = "Equation")},
            singleLine = true,
            modifier = Modifier.width(350.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            Button(
                onClick = {
                    result = try {
                        val exp = ExpressionBuilder(equation).build()
                        exp.evaluate().toString()
                    }catch (e: Exception){
                        "NaN"
                    }
                },
                modifier = Modifier.width(130.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Orange_kt)
            ) {
                Text(
                    text = "Calculate",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.width(130.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Purple_kt)
            ) {
                Text(
                    text = "Exit",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
        Text(
            text = "Result:",
            fontSize = 25.sp,
            modifier = Modifier.padding(top = 15.dp)
        )
        Text(
            text = result,
            fontSize = 30.sp,
            modifier = Modifier.padding(10.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
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
                    text = "Tugas2",
                    fontWeight = FontWeight.Bold,
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
        )
    }
}