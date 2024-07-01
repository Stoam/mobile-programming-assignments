package com.example.tugas45678.di

import android.app.Application
import androidx.room.Room
import com.example.tugas45678.data.MhsDB
import com.example.tugas45678.data.MhsRepository
import com.example.tugas45678.data.MhsRepositoryImpl
import com.example.tugas45678.data.api.Api
import com.example.tugas45678.data.api.Api2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MhsModule {

    @Provides
    @Singleton
    fun provideMhsDB(application: Application): MhsDB{
        return Room.databaseBuilder(
            application,
            MhsDB::class.java,
            "mahasiswa_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    @Named("Api1")
    fun provideRetrofitInstance1(): Api{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://gist.githubusercontent.com/")
            .build()
            .create(Api::class.java)
    }

    @Provides
    @Singleton
    @Named("Api2")
    fun provideRetrofitInstance2(): Api2 {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://192.168.1.107:5000")
            .build()
            .create(Api2::class.java)
    }

    @Provides
    @Singleton
    fun provideMhsRepository(
        db: MhsDB,
        @Named("Api1") api: Api,
        @Named("Api2") api2: Api2
    ): MhsRepository{
        return MhsRepositoryImpl(db.dao, api, api2)
    }
}