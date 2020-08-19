package com.nexters.zzallang.harusal2.ui.statement.edit

import com.nexters.zzallang.harusal2.base.BaseViewModel

class StatementDetailViewModel: BaseViewModel() {
    private lateinit var date: String
    private lateinit var amount: String
    private lateinit var memo: String


    fun getDate(): String{
        return date
    }

    fun getAmount(): String{
        return amount
    }

    fun getMemo(): String{
        return memo
    }

    fun setDate(date: String){
        this.date = date
    }

    fun setAmount(amount: String){
        this.amount = amount
    }

    fun setMemo(memo: String){
        this.memo = memo
    }

}