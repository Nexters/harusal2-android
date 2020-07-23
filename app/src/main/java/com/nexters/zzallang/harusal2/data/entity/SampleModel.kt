package com.nexters.zzallang.harusal2.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sample_table")
data class SampleModel(
    @PrimaryKey @ColumnInfo(name = "sample_id") val sampleId: Long,
    @ColumnInfo(name = "sample_name") val sampleName: String
)