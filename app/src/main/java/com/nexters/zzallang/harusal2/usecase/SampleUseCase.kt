package com.nexters.zzallang.harusal2.usecase

import com.nexters.zzallang.harusal2.data.entity.SampleModel
import com.nexters.zzallang.harusal2.data.repository.SampleRepository

class SampleUseCase(private val sampleRepo: SampleRepository) {
    suspend fun sampleGetData(sampleId: Long): SampleModel {
        return sampleRepo.getData(sampleId)
    }

    suspend fun sampleInsertData(sampleData: SampleModel) {
        sampleRepo.insertData(sampleData)
    }
}