package com.nexters.zzallang.harusal2.data.repository

import com.nexters.zzallang.harusal2.data.model.SampleModel
import com.nexters.zzallang.harusal2.application.AppDatabase

class SampleRepository {
    suspend fun insertData(sampleModel: SampleModel) {
        AppDatabase.instance.sampleDao().insert(sampleModel)
    }

    suspend fun getData(sampleId: Long) =
        AppDatabase.instance.sampleDao().getModel(sampleId)
}