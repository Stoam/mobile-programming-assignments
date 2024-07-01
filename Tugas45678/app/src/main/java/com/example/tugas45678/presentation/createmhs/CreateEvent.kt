package com.example.tugas45678.presentation.createmhs

sealed class CreateEvent {
    data class OnNrpChange(val nrp: String): CreateEvent()
    data class OnNamaChange(val nama: String): CreateEvent()
    data object OnAddButtonClick: CreateEvent()
}