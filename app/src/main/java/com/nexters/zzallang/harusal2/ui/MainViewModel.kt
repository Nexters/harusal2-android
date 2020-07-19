package com.nexters.zzallang.harusal2.ui

import android.util.Log
import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.data.model.SampleModel
import com.nexters.zzallang.harusal2.usecase.SampleUseCase
import kotlinx.coroutines.launch

class MainViewModel(private val sampleUseCase: SampleUseCase) : BaseViewModel() {
    fun insertSampleData(sampleData: SampleModel) {
        launch {
            sampleUseCase.sampleInsertData(sampleData)
        }
    }

    fun checkSampleData(sampleId: Long) {
        launch {
            val data = sampleUseCase.sampleGetData(sampleId)

            Log.d("dataId: ", data.sampleId.toString())
            Log.d("dataName: ", data.sampleName)
        }
    }
}