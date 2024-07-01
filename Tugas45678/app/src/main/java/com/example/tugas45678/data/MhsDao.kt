package com.example.tugas45678.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MhsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createMhs(mhs: Mhs)

    @Query("SELECT * FROM mahasiswa")
    fun getAllMhs(): Flow<List<Mhs>>

    @Query("SELECT * FROM mahasiswa WHERE nrp = :nrp")
    suspend fun findMhs(nrp: String): Mhs?

    @Query("UPDATE mahasiswa SET nrp = :newNrp, nama = :newNama, imageUri = :newImageUri WHERE nrp = :oldNrp")
    suspend fun updateMhs(oldNrp: String, newNrp: String, newNama: String, newImageUri: String)

    @Delete
    suspend fun deleteMhs(mhs: Mhs)
}