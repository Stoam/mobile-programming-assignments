package com.example.tugas45678.presentation.allmhs

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugas45678.data.MhsRepository
import com.example.tugas45678.data.api.models.Contact
import com.example.tugas45678.util.GeneralEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllMhsViewModel @Inject constructor(
    private val repository: MhsRepository
): ViewModel() {

    private val _generalEvent = Channel<GeneralEvent>()
    val generalEvent = _generalEvent.receiveAsFlow()

    val allMhs = repository.getAllMhs()

    private var _contactsFromApi = MutableStateFlow<List<Contact>>(emptyList())
    var contactsFromApi = _contactsFromApi.asStateFlow()

    init {
        Log.d("Initial contact list", "with size ${_contactsFromApi.value.size}")
        viewModelScope.launch {
            repository.getContacts().collect(_contactsFromApi)
            Log.d("Updated contact list", "current size ${_contactsFromApi.value.size}")
        }
    }

    private fun sendGeneralEvent(event: GeneralEvent) {
        viewModelScope.launch {
            _generalEvent.send(event)
        }
    }
}