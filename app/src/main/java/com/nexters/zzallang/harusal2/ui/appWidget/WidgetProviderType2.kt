package com.nexters.zzallang.harusal2.ui.appWidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.ui.splash.SplashActivity
import com.nexters.zzallang.harusal2.usecase.GetRemainDayUseCase
import com.nexters.zzallang.harusal2.usecase.GetSpentMoneyStatusUseCase
import com.nexters.zzallang.harusal2.usecase.GetTodayBudgetUseCase
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class WidgetProviderType2 : AppWidgetProvider(), KoinComponent {

    private val getRemainDayUseCase: GetRemainDayUseCase by inject()
    private val getTodayBudgetUseCase : GetTodayBudgetUseCase by inject()
    private val getRemainMoneyUseCase : GetRemainDayUseCase by inject()
    private val getSpentMoneyStatusUseCase : GetSpentMoneyStatusUseCase by inject()

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        appWidgetIds?.forEach { appWidgetId ->
            RemoteViews(context?.packageName, R.layout.widget_type2).also {

                it.setOnClickOpenApp(context, appWidgetId)
                it.setOnClickRefresh(context, appWidgetId)
                appWidgetManager?.updateAppWidget(appWidgetId, it)
            }
        }
    }

    private fun RemoteViews.setOnClickOpenApp(context : Context?, appWidgetId : Int){
        val pendingIntent = PendingIntent.getActivity(
            context,
            APP_WIDGET_REQUEST_CODE2 + appWidgetId,
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
            APP_WIDGET_REQUEST_CODE2 + appWidgetId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        setOnClickPendingIntent(R.id.iv_refresh, pendingIntent)
    }

    private fun initData(){


    }

    companion object{
        const val APP_WIDGET_REQUEST_CODE2 = 102
    }
}