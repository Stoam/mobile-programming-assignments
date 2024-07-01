package com.example.tugas45678.presentation.updatescreen

import android.net.Uri
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
class UpdateViewModel @Inject constructor(
    private val repository: MhsRepository
) : ViewModel() {

    private val _generalEvent = Channel<GeneralEvent>()
    val generalEvent = _generalEvent.receiveAsFlow()

    var nrp by mutableStateOf("")
        private set

    var mhs by mutableStateOf<Mhs?>(null)
        private set

    var showDialog by mutableStateOf(false)

    var dialogNrp by mutableStateOf("")
        private set

    var dialogNama by mutableStateOf("")
        private set

    var dialogImageUri by mutableStateOf<Uri?>(null)

    var showGetImageDialog by mutableStateOf(false)

    fun onEvent(event: UpdateEvent){
        when(event){
            is UpdateEvent.OnNrpChange -> {
                nrp = event.nrp
            }
            is UpdateEvent.OnFindButtonClick -> {
                if (nrp.isEmpty()){
                    sendGeneralEvent(GeneralEvent.ShowSnackbar(
                        "Field may not be empty", "Dismiss"
                    ))
                }else{
                    viewModelScope.launch {
                        mhs = repository.findMhs(nrp)
                        if (mhs != null){
                            dialogNrp = mhs!!.nrp
                            dialogNama = mhs!!.nama
                            dialogImageUri = Uri.parse(mhs!!.imageUri)
                            showDialog = true
                        }else{
                            sendGeneralEvent(GeneralEvent.ShowSnackbar(
                                "Entry not found", "Dismiss"
                            ))
                        }
                    }
                }
            }
            is UpdateEvent.OnDialogNrpChange -> {
                dialogNrp = event.nrp
            }
            is UpdateEvent.OnDialogNamaChange -> {
                dialogNama = event.nama
            }
            is UpdateEvent.OnDialogUpdateButtonClick -> {
                if (dialogNrp.isEmpty() || dialogNama.isEmpty()){
                    sendGeneralEvent(GeneralEvent.ShowSnackbar(
                        "Field may not be empty", "Dismiss"
                    ))
                }else if (dialogImageUri == null){
                    sendGeneralEvent(GeneralEvent.ShowSnackbar(
                        "Please select an image", "Dismiss"
                    ))
                }else{
                    viewModelScope.launch {
                        if (isExist(dialogNrp)){
                            sendGeneralEvent(GeneralEvent.ShowSnackbar(
                                "Duplicate NRP is not allowed", "Dismiss"
                            ))
                        }else{
                            repository.updateMhs(nrp, dialogNrp, dialogNama, dialogImageUri!!.toString())
                            sendGeneralEvent(GeneralEvent.ShowSnackbar(
                                "Entry $dialogNrp - $dialogNama updated successfully", "Dismiss"
                            ))
                            showDialog = false
                            mhs = null
                            nrp = ""
                            dialogNrp = ""
                            dialogNama = ""
                            dialogImageUri = null
                        }
                    }
                }
            }
            is UpdateEvent.OnDialogCancelButtonClick -> {
                showDialog = false
                mhs = null
                dialogNrp = ""
                dialogNama = ""
                dialogImageUri = null
            }
        }
    }

    private suspend fun isExist(dialogNrp: String): Boolean{
        val mhs = repository.findMhs(dialogNrp)
        if (mhs != null){
            if (mhs.nrp == nrp){
                return false
            }else{
                return true
            }
        }else{
            return false
        }
    }

    private fun sendGeneralEvent(event: GeneralEvent) {
        viewModelScope.launch {
            _generalEvent.send(event)
        }
    }
}