package com.example.tugas45678.presentation.readmhs

import java.io.File

sealed class ReadEvent {
    data class OnNrpChange(val nrp: String): ReadEvent()
    data object OnFindButtonClick: ReadEvent()
    data class SendSelectedImage(val file: File): ReadEvent()
    data object SendSelectedName: ReadEvent()
}