package com.example.tugas45678.presentation.deletemhs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tugas45678.ui.theme.Purple_kt
import com.example.tugas45678.util.GeneralEvent

@Composable
fun DeleteScreen(
    viewModel: DeleteViewModel = hiltViewModel(),
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
            text = "Delete Entry by NRP",
            color = Purple_kt,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(vertical = 15.dp)
        )
        OutlinedTextField(
            value = viewModel.nrp,
            onValueChange = {
                viewModel.onEvent(DeleteEvent.OnNrpChange(it))
            },
            label = { Text(text = "NRP") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        )
        Button(
            onClick = {
                viewModel.onEvent(DeleteEvent.OnDeleteButtonClick)
                focusManager.clearFocus()
            },
            colors = ButtonDefaults.buttonColors(Purple_kt),
            shape = RoundedCornerShape(7.dp),
            modifier = Modifier
                .width(280.dp)
                .padding(top = 15.dp)
        ) {
            Text(
                text = "Delete",
                fontSize = 18.sp
            )
        }

        if (viewModel.showDialog){
            DeleteConfirmation(viewModel)
        }
    }
}

@Composable
fun DeleteConfirmation(viewModel: DeleteViewModel) {
    Dialog(onDismissRequest = {
        viewModel.showDialog = false
    }) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Are you sure you want to delete",
                    fontSize = 18.sp
                )
                Text(
                    text = "${viewModel.mhs!!.nrp} - ${viewModel.mhs!!.nama}?",
                    fontSize = 18.sp
                )
                Text(
                    text = "note: deletion cannot be undone",
                    color = Color.Gray,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.padding(top = 10.dp)
                )
                Button(
                    onClick = {
                        viewModel.onEvent(DeleteEvent.OnDeleteDialogButtonClick)
                    },
                    colors = ButtonDefaults.buttonColors(Purple_kt),
                    shape = RoundedCornerShape(7.dp),
                    modifier = Modifier
                        .width(250.dp)
                        .padding(top = 15.dp)
                ) {
                    Text(
                        text = "Delete",
                        fontSize = 18.sp)
                }
                OutlinedButton(
                    onClick = {
                        viewModel.onEvent(DeleteEvent.OnCancelDialogButtonClick)
                    },
                    shape = RoundedCornerShape(7.dp),
                    border = BorderStroke(1.dp, Color.Gray),
                    modifier = Modifier.width(250.dp)
                ) {
                    Text(
                        text = "Cancel",
                        fontSize = 18.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}