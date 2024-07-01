package com.example.tugas45678.data

import com.example.tugas45678.data.api.models.Contact
import kotlinx.coroutines.flow.Flow
import java.io.File

interface MhsRepository {

    suspend fun createMhs(mhs: Mhs)

    fun getAllMhs(): Flow<List<Mhs>>

    suspend fun findMhs(nrp: String): Mhs?

    suspend fun updateMhs(oldNrp: String, newNrp: String, newNama: String, newImageUri: String)

    suspend fun deleteMhs(mhs: Mhs)

    suspend fun getContacts(): Flow<List<Contact>>

    suspend fun uploadImage(file: File): Boolean

    suspend fun uploadString(name: String)
}