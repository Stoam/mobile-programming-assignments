package com.example.tugas45678.presentation.readmhs

import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.tugas45678.R
import com.example.tugas45678.ui.theme.Orange_kt
import com.example.tugas45678.ui.theme.Pink_kt
import com.example.tugas45678.util.GeneralEvent
import com.example.tugas45678.util.UriManager

@Composable
fun ReadScreen(
    viewModel: ReadViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    snackbarHostState: SnackbarHostState
) {
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = true) {
        viewModel.generalEvent.collect{event ->
            when(event){
                is GeneralEvent.ShowSnackbar -> {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    val result = snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                    if (result == SnackbarResult.ActionPerformed){
                        snackbarHostState.currentSnackbarData?.dismiss()
                    }
                }
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit){ detectTapGestures(onTap = {focusManager.clearFocus()}) }
            .padding(paddingValues)
    ) {
        Text(
            text = "Find Entry by NRP",
            color = Pink_kt,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(vertical = 15.dp)
        )
        OutlinedTextField(
            value = viewModel.nrp,
            onValueChange = {
                viewModel.onEvent(ReadEvent.OnNrpChange(it))
            },
            label = { Text(text = "NRP") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
        )
        Button(
            onClick = {
                viewModel.onEvent(ReadEvent.OnFindButtonClick)
                focusManager.clearFocus()
            },
            colors = ButtonDefaults.buttonColors(Pink_kt),
            shape = RoundedCornerShape(7.dp),
            modifier = Modifier
                .width(280.dp)
                .padding(top = 15.dp)
        ) {
            Text(
                text = "Find",
                fontSize = 18.sp
            )
        }

        if (viewModel.showResult){
            ShowResult(viewModel = viewModel)
        }
    }
}

@Composable
fun ShowResult(viewModel: ReadViewModel) {
    val context = LocalContext.current

    Text(
        text = "Result:",
        fontSize = 20.sp,
        modifier = Modifier.padding(top = 15.dp)
    )
    AsyncImage(
        fallback = painterResource(id = R.drawable.profile_placeholder),
        model = Uri.parse(viewModel.mhs!!.imageUri),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(top = 30.dp)
            .size(200.dp)
            .clip(CircleShape)
    )
    Row(
        modifier = Modifier.padding(horizontal = 40.dp).padding(top = 40.dp)
    ) {
        Column(
            modifier = Modifier.weight(.3f)
        ) {
            Text(
                text = "NRP:",
                Modifier
                    .border(1.dp, Color.Black)
                    .padding(8.dp)
                    .fillMaxWidth()
            )
            Text(
                text = "Nama:",
                Modifier
                    .border(1.dp, Color.Black)
                    .padding(8.dp)
                    .fillMaxWidth()
            )
        }
        Column(
            modifier = Modifier.weight(.7f)
        ) {
            Text(
                text = viewModel.mhs!!.nrp,
                Modifier
                    .border(1.dp, Color.Black)
                    .padding(8.dp)
                    .fillMaxWidth()
            )
            Text(
                text = viewModel.mhs!!.nama,
                Modifier
                    .border(1.dp, Color.Black)
                    .padding(8.dp)
                    .fillMaxWidth()
            )
        }
    }
    Button(
        onClick = {
            val file = UriManager.getFileFromUri(context, Uri.parse(viewModel.mhs!!.imageUri))
            viewModel.onEvent(ReadEvent.SendSelectedImage(file))
//            viewModel.onEvent(ReadEvent.SendSelectedName)
        },
        colors = ButtonDefaults.buttonColors(Orange_kt),
        shape = RoundedCornerShape(7.dp),
        modifier = Modifier
            .width(280.dp)
            .padding(top = 15.dp)
    ) {
        Text(
            text = "Send",
            fontSize = 18.sp
        )
    }
}