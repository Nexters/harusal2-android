package com.nexters.zzallang.harusal2.ui.appWidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.ui.splash.SplashActivity

class WidgetProviderType1 : AppWidgetProvider() {
    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        appWidgetIds?.forEach { appWidgetId ->
                RemoteViews(context?.packageName, R.layout.widget_type1).also {

                    it.setOnClickOpenApp(context, appWidgetId)
                    it.setOnClickRefresh(context, appWidgetId)
                    appWidgetManager?.updateAppWidget(appWidgetId, it)
                }
        }
    }

    private fun RemoteViews.setOnClickOpenApp(context : Context?, appWidgetId : Int){
        val pendingIntent = PendingIntent.getActivity(
            context,
            APP_WIDGET_REQUEST_CODE + appWidgetId,
            Intent(context, SplashActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        setOnClickPendingIntent(R.id.container, pendingIntent)
    }

    private fun RemoteViews.setOnClickRefresh(context : Context?, appWidgetId : Int){
        val intent = Intent(context, WidgetProviderType1::class.java).apply {
            action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, intArrayOf(appWidgetId))
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            APP_WIDGET_REQUEST_CODE + appWidgetId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        setOnClickPendingIntent(R.id.iv_refresh, pendingIntent)
    }

    companion object{
        const val APP_WIDGET_REQUEST_CODE = 100
    }
}