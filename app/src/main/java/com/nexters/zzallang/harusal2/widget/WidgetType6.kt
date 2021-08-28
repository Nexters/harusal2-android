package com.nexters.zzallang.harusal2.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.ui.statement.register.AddStatementActivity

class WidgetType6 : AppWidgetProvider() {
    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        appWidgetIds?.forEach { appWidgetId ->
            RemoteViews(context?.packageName, R.layout.widget_type_6).also {
                it.setOnClickOpenApp(context)

                appWidgetManager?.updateAppWidget(appWidgetId, it)
            }
        }
    }

    private fun RemoteViews.setOnClickOpenApp(context: Context?) {
        val pendingIntent = PendingIntent.getActivity(
            context,
            APP_OPEN_REQUEST_CODE,
            Intent(context, AddStatementActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )
        setOnClickPendingIntent(R.id.btn_move_add_statement, pendingIntent)
    }

    companion object {
        const val APP_OPEN_REQUEST_CODE = 100
    }
}