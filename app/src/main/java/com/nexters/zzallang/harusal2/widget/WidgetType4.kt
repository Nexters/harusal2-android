package com.nexters.zzallang.harusal2.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.application.util.NumberUtils
import com.nexters.zzallang.harusal2.usecase.GetRemainMoneyUseCase
import com.nexters.zzallang.harusal2.usecase.GetTodayBudgetUseCase
import kotlinx.coroutines.*
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


@KoinApiExtension
class WidgetType4: AppWidgetProvider(), KoinComponent {
    private val job = Job()

    private val getRemainMoneyUseCase: GetRemainMoneyUseCase by inject()
    private val getTodayBudgetUseCase: GetTodayBudgetUseCase by inject()

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        CoroutineScope(Dispatchers.Main + job).launch {
            appWidgetIds?.forEach { appWidgetId ->
                RemoteViews(context?.packageName, R.layout.widget_type_4).also {
                    it.setTextViewText(R.id.tv_remain_money, NumberUtils.decimalFormat.format(getRemainMoney()))
                    it.setOnRefreshClick(context, appWidgetId)

                    appWidgetManager?.updateAppWidget(appWidgetId, it)
                }
            }
        }
    }

    private suspend fun getRemainMoney() =
        getRemainMoneyUseCase.getRemainMoney(getTodayBudgetUseCase.getTodayBudget())

    private fun RemoteViews.setOnRefreshClick(context: Context?, appWidgetId: Int) {
        val intent = Intent(context, WidgetType4::class.java).apply {
            action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, intArrayOf(appWidgetId) )
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REFRESH_REQUEST_CODE + appWidgetId,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        setOnClickPendingIntent(R.id.btn_refresh, pendingIntent)
    }

    companion object {
        const val REFRESH_REQUEST_CODE = 100
    }
}