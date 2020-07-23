package com.nexters.zzallang.harusal2.ui

import androidx.lifecycle.MutableLiveData
import com.nexters.zzallang.harusal2.base.BaseViewModel
import com.nexters.zzallang.harusal2.data.entity.Statement
import com.nexters.zzallang.harusal2.usecase.StatementUseCase
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(private val statementUseCase: StatementUseCase) : BaseViewModel() {
    val data = MutableLiveData<Statement>()
    val id = MutableLiveData<String>()

    fun insertData() {
        launch {
            statementUseCase.insertData(Statement(1L, 1L, Date(), "first", 100, 1))
        }
    }

    fun getData() {
        launch {
            data.postValue(statementUseCase.getData(id.value!!.toLong()))
        }
    }
}