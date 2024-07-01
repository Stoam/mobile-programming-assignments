package com.example.tugas45678.presentation.updatescreen

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.tugas45678.R
import com.example.tugas45678.ui.theme.Orange_kt
import com.example.tugas45678.util.CameraFileProvider
import com.example.tugas45678.util.GeneralEvent
import com.example.tugas45678.util.UriManager
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@Composable
fun UpdateScreen(
    viewModel: UpdateViewModel = hiltViewModel(),
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
            .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) }
            .padding(paddingValues)
    ) {
        Text(
            text = "Update Entry by NRP",
            color = Orange_kt,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(vertical = 15.dp)
        )
        OutlinedTextField(
            value = viewModel.nrp,
            onValueChange = {
                viewModel.onEvent(UpdateEvent.OnNrpChange(it))
            },
            label = { Text(text = "NRP") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        )
        Button(
            onClick = {
                viewModel.onEvent(UpdateEvent.OnFindButtonClick)
                focusManager.clearFocus()
            },
            colors = ButtonDefaults.buttonColors(Orange_kt),
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
        if (viewModel.showDialog) {
            UpdateDialog(viewModel, snackbarHostState)
        }
    }
}


@Composable
fun UpdateDialog(
    viewModel: UpdateViewModel,
    snackbarHostState: SnackbarHostState
) {

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

    Dialog(onDismissRequest = {
        viewModel.showDialog = false
    }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(30.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "UPDATE",
                    color = Orange_kt,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 15.dp)
                )
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(top = 10.dp, bottom = 30.dp)
                ) {
                    AsyncImage(
                        fallback = painterResource(id = R.drawable.profile_placeholder),
                        model = viewModel.dialogImageUri,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape)
                    )
                    Button(
                        onClick = {
                            viewModel.showGetImageDialog = true
                        },
                        shape = CircleShape,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(55.dp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(Orange_kt)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null
                        )
                    }
                }
                OutlinedTextField(
                    value = viewModel.dialogNrp,
                    onValueChange = {
                        viewModel.onEvent(UpdateEvent.OnDialogNrpChange(it))
                    },
                    label = { Text(text = "NRP") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    )
                )
                OutlinedTextField(
                    value = viewModel.dialogNama,
                    onValueChange = {
                        viewModel.onEvent(UpdateEvent.OnDialogNamaChange(it))
                    },
                    label = { Text(text = "Nama") },
                    modifier = Modifier.padding(top = 7.dp),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    )
                )
                Button(
                    onClick = {
                        viewModel.onEvent(UpdateEvent.OnDialogUpdateButtonClick)
                    },
                    colors = ButtonDefaults.buttonColors(Orange_kt),
                    shape = RoundedCornerShape(7.dp),
                    modifier = Modifier
                        .width(280.dp)
                        .padding(top = 15.dp)
                ) {
                    Text(
                        text = "Update",
                        fontSize = 18.sp)
                }
                OutlinedButton(
                    onClick = {
                        viewModel.onEvent(UpdateEvent.OnDialogCancelButtonClick)
                    },
                    shape = RoundedCornerShape(7.dp),
                    border = BorderStroke(1.dp, Color.Gray),
                    modifier = Modifier
                        .width(280.dp)
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

    if (viewModel.showGetImageDialog){
        GetImageDialog(viewModel = viewModel)
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GetImageDialog(
    viewModel: UpdateViewModel
) {
    val context = LocalContext.current

//    Photo Picker
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {contentUri ->
            if (contentUri != null){
                val fileUri = UriManager.convertContentToFilesUri(context, contentUri)
                viewModel.dialogImageUri = fileUri
                viewModel.showGetImageDialog= false
            }
        }
    )

//    Camera
    val cameraPermissionState = rememberPermissionState(
        permission = Manifest.permission.CAMERA
    )

    val internalFileUri = CameraFileProvider.getContentUri(context)

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = {captured ->
            if (captured){
                val fileUri = UriManager.convertContentToFilesUri(context, internalFileUri)
                viewModel.dialogImageUri = fileUri
                viewModel.showGetImageDialog = false
            }
        }
    )

//    Dialog Composable
    Dialog(onDismissRequest = {
        viewModel.showGetImageDialog = false
    }) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
        ) {
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(30.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                IconButton(
                    onClick = {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Image,
                            contentDescription = "Gallery",
                            modifier = Modifier.size(100.dp)
                        )
                        Text(
                            text = "Gallery",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                IconButton(
                    onClick = {
                        if (cameraPermissionState.status.isGranted){
                            cameraLauncher.launch(internalFileUri)
                        }else{
                            cameraPermissionState.launchPermissionRequest()
                            if (cameraPermissionState.status.isGranted){
                                cameraLauncher.launch(internalFileUri)
                            }
                        }
                    }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PhotoCamera,
                            contentDescription = "Camera",
                            modifier = Modifier.size(100.dp)
                        )
                        Text(
                            text = "Camera",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}