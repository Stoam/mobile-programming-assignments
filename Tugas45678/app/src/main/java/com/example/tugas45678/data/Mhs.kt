package com.example.tugas45678.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mahasiswa")
class Mhs(
    @PrimaryKey(autoGenerate = false) val nrp: String,
    val nama: String,
    val imageUri: String
)