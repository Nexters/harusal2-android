package com.nexters.zzallang.harusal2.data.repository

import com.nexters.zzallang.harusal2.application.AppDatabase
import com.nexters.zzallang.harusal2.base.BaseRepository
import com.nexters.zzallang.harusal2.data.entity.SampleModel
import kotlinx.coroutines.withContext

class SampleRepository : BaseRepository() {
    suspend fun insertData(sampleModel: SampleModel) =
        withContext(coroutineContext) {
            AppDatabase.instance.sampleDao().insert(sampleModel)
        }

    suspend fun getData(sampleId: Long): SampleModel =
        withContext(coroutineContext) {
            AppDatabase.instance.sampleDao().getModel(sampleId)
        }
}