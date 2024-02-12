package com.nexters.zzallang.harusal2.ui.statement.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nexters.zzallang.harusal2.constant.Constants
import com.nexters.zzallang.harusal2.application.util.MoneyUtils

class AddInputViewModel: ViewModel() {
    private var statementType = Constants.STATEMENT_TYPE_OUT
    private val _convertedAmount = MutableLiveData("")
    val statementAmount = MutableLiveData("")
    val convertedAmount : LiveData<String> get() = _convertedAmount

    fun setType(type: Int){
        statementType = type
    }

    fun getType():Int{
        return statementType
    }

    fun clear(){
        _convertedAmount.postValue("")
    }

    fun updateConvertedAmount(amount: String){
        if(amount == ""){
            clear()
            return
        }
        val amountLong: Long = amount.toLong()
        _convertedAmount.postValue(convert(amountLong))
    }

    fun convert(amount: Long): String{
        return MoneyUtils.convertString(java.lang.Long.valueOf(amount))
    }
}
