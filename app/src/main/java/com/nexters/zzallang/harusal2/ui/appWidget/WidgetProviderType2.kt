package com.nexters.zzallang.harusal2.ui.appWidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.nexters.zzallang.harusal2.R

class WidgetProviderType2 : AppWidgetProvider() {

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        appWidgetIds?.forEach { appWidgetId ->
            RemoteViews(context?.packageName, R.layout.widget_type2).also {


                appWidgetManager?.updateAppWidget(appWidgetId, it)
            }
        }
    }
}