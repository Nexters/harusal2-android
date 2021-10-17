package com.nexters.zzallang.harusal2.ui.appWidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.content.ContextCompat
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.constant.SpendState
import com.nexters.zzallang.harusal2.ui.splash.SplashActivity
import com.nexters.zzallang.harusal2.usecase.GetRemainDayUseCase
import com.nexters.zzallang.harusal2.usecase.GetRemainMoneyUseCase
import com.nexters.zzallang.harusal2.usecase.GetSpentMoneyStatusUseCase
import com.nexters.zzallang.harusal2.usecase.GetTodayBudgetUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class WidgetProviderType2 : AppWidgetProvider(), KoinComponent {

    private val getRemainDayUseCase: GetRemainDayUseCase by inject()
    private val getTodayBudgetUseCase : GetTodayBudgetUseCase by inject()
    private val getRemainMoneyUseCase : GetRemainMoneyUseCase by inject()
    private val getSpentMoneyStatusUseCase : GetSpentMoneyStatusUseCase by inject()

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        if(context == null) return
        CoroutineScope(Dispatchers.IO).launch {
            appWidgetIds?.forEach { appWidgetId ->
                RemoteViews(context.packageName, R.layout.widget_type2).also {

                    val remainDay = getRemainDayUseCase.getRemainDay()
                    val todayBudget = getTodayBudgetUseCase.getTodayBudget()
                    val remainMoney = getRemainMoneyUseCase.getRemainMoney(todayBudget)
                    val spendState = getSpentMoneyStatusUseCase.getSpentMoneyStatus(todayBudget, remainMoney)

                    it.updateView(context, appWidgetId, remainDay, spendState)
                    it.setOnClickOpenApp(context, appWidgetId)
                    it.setOnClickRefresh(context, appWidgetId)
                    appWidgetManager?.updateAppWidget(appWidgetId, it)
                }
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
        val intent = Intent(context,
            WidgetProviderType2::class.java).apply {
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

    private fun RemoteViews.updateView(context: Context, appWidgetId: Int, remainDay: Int, spendState: SpendState){
        setInt(R.id.widget_bg2, "setColorFilter", ContextCompat.getColor(context, SpendState.getBackgroundColor(spendState)))
        setTextViewText(R.id.tv_dday, WidgetProviderType2.REMAIN_DAY_PREFIX +remainDay)
        setImageViewResource(R.id.iv_emoji, SpendState.getEmojiImage(spendState))
    }

    companion object{
        const val APP_WIDGET_REQUEST_CODE2 = 102
        const val REMAIN_DAY_PREFIX = "D-"
    }
}