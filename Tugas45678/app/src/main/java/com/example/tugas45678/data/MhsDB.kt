package com.example.tugas45678.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Mhs::class],
    version = 2,
    exportSchema = false
)
abstract class MhsDB: RoomDatabase() {

    abstract val dao: MhsDao
}