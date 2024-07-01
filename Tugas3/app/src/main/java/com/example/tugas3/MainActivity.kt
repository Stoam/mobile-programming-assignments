package com.example.tugas3

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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tugas3.ui.theme.Blue_kt
import com.example.tugas3.ui.theme.Orange_kt
import com.example.tugas3.ui.theme.Pink_kt
import com.example.tugas3.ui.theme.Purple_kt
import com.example.tugas3.ui.theme.Tugas3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tugas3Theme {
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

@Preview(showBackground = true)
@Composable
fun MainApp() {
    Scaffold(topBar = { AppBar()}) {values ->
        Calculator(values = values)
    }
}

@Composable
fun Calculator(values: PaddingValues) {
    var num1 by remember { mutableStateOf(TextFieldValue("")) }
    var num2 by remember { mutableStateOf(TextFieldValue("")) }
    var result by remember { mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(values)
            .padding(start = 20.dp, end = 20.dp, top = 20.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 20.dp)
            ) {
                Text(
                    text = "Num 1",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                OutlinedTextField(
                    value = num1,
                    onValueChange = {num1 = it},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 20.dp)
            ) {
                Text(
                    text = "Num 2",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                OutlinedTextField(
                    value = num2,
                    onValueChange = {num2 = it},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Button(
                onClick = {
                    result = doMath("+", num1, num2)
                    num1 = TextFieldValue("")
                    num2 = TextFieldValue("")
                },
                colors = ButtonDefaults.buttonColors(Blue_kt),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 3.dp),
            ) {
                Text(
                    text = "+",
                    fontSize = 20.sp
                )
            }
            Button(
                onClick = {
                    result = doMath("-", num1, num2)
                    num1 = TextFieldValue("")
                    num2 = TextFieldValue("")
                },
                colors = ButtonDefaults.buttonColors(Pink_kt),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 3.dp),
            ) {
                Text(
                    text = "-",
                    fontSize = 20.sp
                )
            }
            Button(
                onClick = {
                    result = doMath("×", num1, num2)
                    num1 = TextFieldValue("")
                    num2 = TextFieldValue("")
                },
                colors = ButtonDefaults.buttonColors(Orange_kt),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 3.dp),
            ) {
                Text(
                    text = "×",
                    fontSize = 20.sp
                )
            }
            Button(
                onClick = {
                    result = doMath("÷", num1, num2)
                    num1 = TextFieldValue("")
                    num2 = TextFieldValue("")
                },
                colors = ButtonDefaults.buttonColors(Purple_kt),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 3.dp),
            ) {
                Text(
                    text = "÷",
                    fontSize = 20.sp
                )
            }
        }
        Text(
            text = "Result:",
            fontSize = 25.sp,
            modifier = Modifier.padding(vertical = 15.dp)
        )
        Text(
            text = result,
            fontSize = 25.sp
        )
    }
}

fun doMath(sign: String, num1: TextFieldValue, num2: TextFieldValue): String{
    val numeric1 = num1.text.toDoubleOrNull()?: return "NaN"
    val numeric2 = num2.text.toDoubleOrNull()?: return "NaN"
    val numericRes = when(sign){
        "+" -> numeric1 + numeric2
        "-" -> numeric1 - numeric2
        "×" -> numeric1 * numeric2
        "÷" -> numeric1 / numeric2
        else -> "NaN"
    }
    return numericRes.toString()
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
                    text = "Tugas3",
                    fontWeight = FontWeight.Bold,
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
        )
    }
}