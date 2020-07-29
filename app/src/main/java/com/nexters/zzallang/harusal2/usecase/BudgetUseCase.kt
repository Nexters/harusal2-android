package com.nexters.zzallang.harusal2.usecase

import android.content.Context
import android.content.SharedPreferences
import com.nexters.zzallang.harusal2.application.App
import com.nexters.zzallang.harusal2.application.util.Constants


class BudgetUseCase {
    private val sharedPreferences: SharedPreferences = App.app.applicationContext.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)
    private val prefEditor: SharedPreferences.Editor = sharedPreferences.edit()

    fun setAmount(amount: Long){
        prefEditor.putLong("amount", amount).apply()
    }

    fun setStartDate(startDate: Int){
        prefEditor.putInt("start_date", startDate).apply()
    }

    fun getAmount(): Long {
        return sharedPreferences.getLong("amount", Constants.DEFAULT_AMOUNT)
    }

    fun getStartDate(): Int {
        return sharedPreferences.getInt("start_date", Constants.DEFAULT_STARTDATE)
    }

    fun removeAmount(){
        prefEditor.remove("amount").apply()
    }

    fun removeStartDate(){
        prefEditor.remove("start_date").apply()
    }

    fun clearBudget(){
        prefEditor.clear().apply()
    }
}