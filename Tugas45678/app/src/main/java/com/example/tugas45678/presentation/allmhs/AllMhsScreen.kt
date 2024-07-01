package com.example.tugas45678.presentation.allmhs

import android.net.Uri
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.tugas45678.R
import com.example.tugas45678.ui.theme.Blue_kt
import com.example.tugas45678.ui.theme.Orange_kt
import com.example.tugas45678.ui.theme.Pink_kt
import com.example.tugas45678.ui.theme.Purple_kt
import com.example.tugas45678.util.GeneralEvent

@Composable
fun AllMhsScreen(
    viewModel: AllMhsViewModel = hiltViewModel(),
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

    val mhsList = viewModel.allMhs.collectAsState(initial = emptyList())
    val contacts = viewModel.contactsFromApi.collectAsState()

    val colorList = listOf(
        Blue_kt,
        Pink_kt,
        Orange_kt,
        Purple_kt
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) }
            .padding(paddingValues)
            .padding(top = 15.dp)
    ) {
        itemsIndexed(mhsList.value){ index, mhs ->
            Card(
                backgroundColor = colorList[index%4],
                elevation = 10.dp,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .padding(bottom = 15.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(16.dp)
                ) {
                    AsyncImage(
                        fallback = painterResource(id = R.drawable.profile_placeholder),
                        model = Uri.parse(mhs.imageUri),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                    )
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.padding(start = 16.dp)
                    ) {

                        Text(
                            text = mhs.nrp,
                            fontSize = 20.sp,
                        )
                        Text(
                            text = mhs.nama,
                            fontSize = 20.sp,
                        )
                    }
                }
            }
        }

        itemsIndexed(contacts.value){index, contact ->
            Card(
                backgroundColor = colorList[index%4],
                elevation = 10.dp,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .padding(bottom = 15.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Column(
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(text = contact.id, fontSize = 16.sp)
                    Text(text = contact.name, fontSize = 22.sp)
                    Text(text = contact.email, fontSize = 18.sp)
                    Text(text = contact.phone.mobile, fontSize = 18.sp)
                }
            }
        }
    }
}