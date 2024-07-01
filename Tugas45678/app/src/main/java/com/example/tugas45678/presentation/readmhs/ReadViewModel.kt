package com.example.tugas45678.presentation.readmhs

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
class ReadViewModel @Inject constructor(
    private val repository: MhsRepository
): ViewModel() {

    private val _generalEvent = Channel<GeneralEvent>()
    val generalEvent = _generalEvent.receiveAsFlow()

    var nrp by mutableStateOf("")
        private set

    var showResult by mutableStateOf(false)
        private set

    var mhs by mutableStateOf<Mhs?>(null)
        private set

    fun onEvent(event: ReadEvent){
        when(event){
            is ReadEvent.OnFindButtonClick -> {
                viewModelScope.launch {
                    val mhsFound: Mhs? = repository.findMhs(nrp)
                    if (mhsFound != null){
                        mhs = mhsFound
                        showResult = true
                        nrp = ""
                    }else{
                        sendGeneralEvent(GeneralEvent.ShowSnackbar(
                            "Entry not found", "Dismiss"
                        ))
                        showResult = false
                        mhs = null
                    }
                }
            }
            is ReadEvent.OnNrpChange -> {
                nrp = event.nrp
            }

            is ReadEvent.SendSelectedImage -> {
                viewModelScope.launch {
                    Log.d("FILE_IMG", event.file.absolutePath)
                    repository.uploadImage(event.file)
                }
            }

            ReadEvent.SendSelectedName -> {
                viewModelScope.launch {
                    repository.uploadString(mhs!!.nama)
                }
            }
        }
    }

    private fun sendGeneralEvent(event: GeneralEvent) {
        viewModelScope.launch {
            _generalEvent.send(event)
        }
    }
}