package com.nexters.zzallang.harusal2.ui.appWidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.content.ContextCompat
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.application.util.NumberUtils
import com.nexters.zzallang.harusal2.application.util.AppWidgetToastUtil
import com.nexters.zzallang.harusal2.constant.Actions
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
class DDayWidgetProvider : AppWidgetProvider(), KoinComponent {

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
                RemoteViews(context.packageName, R.layout.widget_d_day_emoji_layout).also {

                    val remainDay = getRemainDayUseCase.getRemainDay()
                    val todayBudget = getTodayBudgetUseCase.getTodayBudget()
                    val remainMoney = getRemainMoneyUseCase.getRemainMoney(todayBudget)
                    val spendState = getSpentMoneyStatusUseCase.getSpentMoneyStatus(todayBudget, remainMoney)

                    it.updateView(context, appWidgetId, remainDay, spendState, remainMoney)
                    it.setOnClickOpenApp(context, appWidgetId)
                    it.setOnClickRefresh(context, appWidgetId)
                    appWidgetManager?.updateAppWidget(appWidgetId, it)
                }
            }
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if(intent == null || context == null) return

        val ids = AppWidgetManager.getInstance(context).getAppWidgetIds(ComponentName(context, DDayWidgetProvider::class.java))
        val widget = DDayWidgetProvider()

        if(intent.action.equals(Actions.ACTION_REFRESH)){
            AppWidgetToastUtil.showToast(context)
            widget.onUpdate(context, AppWidgetManager.getInstance(context), ids)
        }
    }

    private fun RemoteViews.setOnClickOpenApp(context : Context?, appWidgetId : Int){
        val pendingIntent = PendingIntent.getActivity(
            context,
            APP_WIDGET_REQUEST_CODE_D_DAY + appWidgetId,
            Intent(context, SplashActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        setOnClickPendingIntent(R.id.container, pendingIntent)
    }

    private fun RemoteViews.setOnClickRefresh(context : Context?, appWidgetId : Int){
        val intent = Intent(context,
            DDayWidgetProvider::class.java).apply {
            action = Actions.ACTION_REFRESH
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, intArrayOf(appWidgetId))
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            APP_WIDGET_REQUEST_CODE_D_DAY + appWidgetId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        setOnClickPendingIntent(R.id.iv_refresh, pendingIntent)
    }

    private fun RemoteViews.updateView(context: Context, appWidgetId: Int, remainDay: Int, spendState: SpendState, remainMoney: Int){
        val remainMoneyText = context.resources?.getString(R.string.appwidget_remaining_money, NumberUtils.decimalFormat.format(remainMoney)).orEmpty()
        setTextViewText(R.id.tv_remain_money, remainMoneyText)
        setInt(R.id.widget_bg2, "setColorFilter", ContextCompat.getColor(context, SpendState.getBackgroundColor(spendState)))
        setTextViewText(R.id.tv_dday, REMAIN_DAY_PREFIX +remainDay)
        setImageViewResource(R.id.iv_emoji, SpendState.getEmojiImage(spendState))
    }

    companion object{
        const val APP_WIDGET_REQUEST_CODE_D_DAY = 102
        const val REMAIN_DAY_PREFIX = "D-"
    }
}