package com.example.tugas45678.presentation.createmhs

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugas45678.data.Mhs
import com.example.tugas45678.data.MhsRepository
import com.example.tugas45678.util.GeneralEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val repository: MhsRepository
) : ViewModel() {

    private val _generalEvent = Channel<GeneralEvent>()
    val generalEvent = _generalEvent.receiveAsFlow()

    var nrp by mutableStateOf("")
        private set

    var nama by mutableStateOf("")
        private set

    var imageUri by mutableStateOf<Uri?>(null)

    var showDialog by mutableStateOf(false)

    fun onEvent(event: CreateEvent){
        when(event){
            is CreateEvent.OnAddButtonClick -> {
                if (nrp.isEmpty() || nama.isEmpty()){
                    sendGeneralEvent(GeneralEvent.ShowSnackbar(
                        "Field may not be empty", "Dismiss"
                    ))
                }else if (imageUri == null){
                    sendGeneralEvent(GeneralEvent.ShowSnackbar(
                        "Please select an image", "Dismiss"
                    ))
                }else{
                    viewModelScope.launch {
                        if (isExist(nrp)){
                            sendGeneralEvent(GeneralEvent.ShowSnackbar(
                                "Duplicate NRP is not allowed", "Dismiss"
                            ))
                        }else{
                            Log.d("ImageURI", imageUri.toString())
                            repository.createMhs(Mhs(nrp, nama, imageUri!!.toString()))
                            sendGeneralEvent(GeneralEvent.ShowSnackbar(
                                "Created entry with $nrp - $nama", "Dismiss"
                            ))
                            nrp = ""
                            nama = ""
                            imageUri = null
                        }
                    }
                }
            }
            is CreateEvent.OnNamaChange -> {
                nama = event.nama
            }
            is CreateEvent.OnNrpChange -> {
                nrp = event.nrp
            }
        }
    }

    private suspend fun isExist(nrp: String): Boolean{
        val mhs = repository.findMhs(nrp)
        return mhs != null
    }

    private fun sendGeneralEvent(event: GeneralEvent) {
        viewModelScope.launch {
            _generalEvent.send(event)
        }
    }
}