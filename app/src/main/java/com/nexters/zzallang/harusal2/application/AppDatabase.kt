package com.nexters.zzallang.harusal2.application

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nexters.zzallang.harusal2.data.dao.SampleDao
import com.nexters.zzallang.harusal2.data.model.SampleModel

@Database(entities = [SampleModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sampleDao(): SampleDao

    companion object {
        val instance: AppDatabase by lazy {
            buildDatabase()
        }

        private fun buildDatabase(): AppDatabase =
            Room.databaseBuilder(App.app, AppDatabase::class.java, "sample.db").build()
    }
}