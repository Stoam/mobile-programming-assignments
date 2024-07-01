package com.example.tugas45678.presentation.deletemhs

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
class DeleteViewModel @Inject constructor(
    private val repository: MhsRepository
): ViewModel() {

    private val _generalEvent = Channel<GeneralEvent>()
    val generalEvent = _generalEvent.receiveAsFlow()

    var nrp by mutableStateOf("")
        private set

    var mhs by mutableStateOf<Mhs?>(null)
        private set

    var showDialog by mutableStateOf(false)

    fun onEvent(event: DeleteEvent){
        when(event){
            is DeleteEvent.OnNrpChange -> {
                nrp = event.nrp
            }
            is DeleteEvent.OnDeleteButtonClick -> {
                if (nrp.isEmpty()){
                    sendGeneralEvent(GeneralEvent.ShowSnackbar(
                        "Field may not be empty", "Dismiss"
                    ))
                }else{
                    viewModelScope.launch {
                        mhs = repository.findMhs(nrp)
                        if (mhs != null){
                            showDialog = true
                        }else{
                            sendGeneralEvent(GeneralEvent.ShowSnackbar(
                                "Entry not found", "Dismiss"
                            ))
                        }
                    }
                }
            }
            is DeleteEvent.OnDeleteDialogButtonClick -> {
                viewModelScope.launch {
                    repository.deleteMhs(mhs!!)
                    sendGeneralEvent(GeneralEvent.ShowSnackbar(
                        "Deleted ${mhs!!.nrp} - ${mhs!!.nama}", "Dismiss"
                    ))
                    nrp = ""
                    mhs = null
                    showDialog = false
                }
            }
            is DeleteEvent.OnCancelDialogButtonClick -> {
                showDialog = false
                mhs = null
            }
        }
    }

    private fun sendGeneralEvent(event: GeneralEvent) {
        viewModelScope.launch {
            _generalEvent.send(event)
        }
    }
}