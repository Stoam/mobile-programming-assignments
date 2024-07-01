package com.example.tugas45678.util

sealed class GeneralEvent {
    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ): GeneralEvent()
}