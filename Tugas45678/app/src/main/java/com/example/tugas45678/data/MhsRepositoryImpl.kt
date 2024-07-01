package com.example.tugas45678.data

import android.util.Log
import com.example.tugas45678.data.api.Api
import com.example.tugas45678.data.api.Api2
import com.example.tugas45678.data.api.models.Contact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class MhsRepositoryImpl @Inject constructor(
    private val dao: MhsDao,
    private val api: Api,
    private val api2: Api2
): MhsRepository {

    override suspend fun createMhs(mhs: Mhs) {
        dao.createMhs(mhs)
    }

    override fun getAllMhs(): Flow<List<Mhs>> {
        return dao.getAllMhs()
    }

    override suspend fun findMhs(nrp: String): Mhs? {
        return dao.findMhs(nrp)
    }

    override suspend fun updateMhs(oldNrp: String, newNrp: String, newNama: String, newImageUri: String) {
        dao.updateMhs(oldNrp, newNrp, newNama, newImageUri)
    }

    override suspend fun deleteMhs(mhs: Mhs) {
        dao.deleteMhs(mhs)
    }

    override suspend fun getContacts(): Flow<List<Contact>>{
        return flow {
            try {
                val contactsFromApi = api.getContacts()
                emit(contactsFromApi.contacts)
            }catch (e: Exception){
                Log.e("API FAILED", "Error when retrieving data")
            }
        }
    }

    override suspend fun uploadImage(file: File): Boolean {
        return try {
            api2.uploadImage(
                image = MultipartBody.Part.createFormData(
                    "image",
                    file.name,
                    file.asRequestBody()
                )
            )
            true
        } catch (e: Exception){
            e.printStackTrace()
            Log.d("FAILED_TO_SEND", "Error")
            false
        }
    }

    override suspend fun uploadString(name: String) {
        api2.uploadString(name)
    }

}