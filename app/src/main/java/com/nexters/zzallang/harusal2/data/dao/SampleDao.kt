package com.nexters.zzallang.harusal2.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nexters.zzallang.harusal2.data.model.SampleModel

@Dao
interface SampleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(model: SampleModel)

    @Query("select * from sample_table where sample_id = :sampleId")
    suspend fun getModel(sampleId: Long): SampleModel
}