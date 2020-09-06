package com.nexters.zzallang.harusal2.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.nexters.zzallang.harusal2.data.DateConverter
import java.util.*

@Entity(tableName = "statement")
@TypeConverters(DateConverter::class)
data class Statement(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0L,
    @ColumnInfo(name = "date") var date: Date,
    @ColumnInfo(name = "content") var content: String,
    @ColumnInfo(name = "amount") var amount: Int,
    @ColumnInfo(name = "type") var type: Int,
    @ColumnInfo(name = "budget_id") var budgetId: Long
) {
    fun summaryContent() : String {
        val SUMMARY_CONTENT_LENGTH = 12
        if (this.content.length > SUMMARY_CONTENT_LENGTH) {
            return this.content.substring(0, SUMMARY_CONTENT_LENGTH) + "..."
        }
        return this.content
    }
}

