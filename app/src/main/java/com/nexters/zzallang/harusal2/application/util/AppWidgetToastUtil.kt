package com.nexters.zzallang.harusal2.application.util

import android.content.Context
import android.widget.Toast
import com.nexters.zzallang.harusal2.R

object AppWidgetToastUtil {
    fun showToast(context: Context){
        Toast.makeText(context, R.string.appwidget_refresh_toast, Toast.LENGTH_LONG).show()
    }
}