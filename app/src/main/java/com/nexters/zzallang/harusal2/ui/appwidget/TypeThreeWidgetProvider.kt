package com.nexters.zzallang.harusal2.ui.appwidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.widget.RemoteViews
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.application.util.NumberUtils
import com.nexters.zzallang.harusal2.constant.SpendState
import com.nexters.zzallang.harusal2.ui.splash.SplashActivity
import com.nexters.zzallang.harusal2.usecase.GetRemainMoneyUseCase
import com.nexters.zzallang.harusal2.usecase.GetSpentMoneyStatusUseCase
import com.nexters.zzallang.harusal2.usecase.GetTodayBudgetUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class TypeThreeWidgetProvider: AppWidgetProvider(), KoinComponent {
	companion object {
		const val THREE_REQUEST_CODE = 100
	}

	private val getTodayBudgetUseCase: GetTodayBudgetUseCase by inject()
	private val getRemainMoneyUseCase: GetRemainMoneyUseCase by inject()
	private val getSpentMoneyStatusUseCase: GetSpentMoneyStatusUseCase by inject()

	private val job = Job()

	override fun onReceive(context: Context?, intent: Intent?) {
		super.onReceive(context, intent)
	}

	override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
		super.onUpdate(context, appWidgetManager, appWidgetIds)

		CoroutineScope(Dispatchers.Main + job).launch {
			appWidgetIds?.forEach { appWidgetId ->
				RemoteViews(context?.packageName, R.layout.appwidget_type_three_layout).also {
					val todayBudget = getTodayBudgetUseCase.getTodayBudget()
					val remainMoney = getRemainMoneyUseCase.getRemainMoney(todayBudget)

					val remainMoneyText = context?.resources?.getString(R.string.appwidget_remaining_money, NumberUtils.decimalFormat.format(remainMoney)).orEmpty()

					it.setImageViewResource(R.id.spent_money_status, getStatus(todayBudget, remainMoney))
					it.setTextViewText(R.id.remain_money, remainMoneyText)

					it.setOnClickRefresh(context, appWidgetId)
					it.setOnClickOpenApp(context, appWidgetId)

					appWidgetManager?.updateAppWidget(appWidgetId, it)
				}
			}
		}
	}

	private fun RemoteViews.setOnClickOpenApp(context: Context?, appWidgetId: Int) {
		val pendingIntent = PendingIntent.getActivity(
			context,
			THREE_REQUEST_CODE + appWidgetId,
			Intent(context, SplashActivity::class.java),
			PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
		)

		setOnClickPendingIntent(R.id.root, pendingIntent)
	}

	private fun RemoteViews.setOnClickRefresh(context: Context?, appWidgetId: Int) {
		val intent = Intent(context, TypeThreeWidgetProvider::class.java).apply {
			action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
			putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, intArrayOf(appWidgetId))
		}

		val pendingIntent = PendingIntent.getBroadcast(
			context,
			THREE_REQUEST_CODE + appWidgetId,
			intent,
			PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
		)

		setOnClickPendingIntent(R.id.refresh, pendingIntent)
	}

	private fun getStatus(todayBudget: Int, remainMoney: Int): Int {
		return when (getSpentMoneyStatusUseCase.getSpentMoneyStatus(todayBudget, remainMoney)) {
			SpendState.FLEX -> R.drawable.appwidget_status_blue
			SpendState.CLAP -> R.drawable.appwidget_status_mint
			SpendState.EMBARRASSED -> R.drawable.appwidget_status_yellow
			SpendState.CRY -> R.drawable.appwidget_status_orange
			else -> R.drawable.appwidget_status_red
		}
	}

	override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
		job.cancel()
		super.onDeleted(context, appWidgetIds)
	}
}
