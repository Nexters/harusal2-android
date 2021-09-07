package com.nexters.zzallang.harusal2.ui.appwidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.application.util.NumberUtils
import com.nexters.zzallang.harusal2.constant.SpendState
import com.nexters.zzallang.harusal2.data.repository.BudgetRepository
import com.nexters.zzallang.harusal2.data.repository.StatementRepository
import com.nexters.zzallang.harusal2.ui.splash.SplashActivity
import com.nexters.zzallang.harusal2.usecase.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TypeThreeWidgetProvider : AppWidgetProvider() {
	companion object {
		const val APP_WIDGET_REQUEST_CODE = 100
	}

	private val budgetRepository = BudgetRepository()
	private val statementRepository = StatementRepository()

	private val getTodayBudgetUseCase = GetTodayBudgetUseCase(budgetRepository, statementRepository)
	private val getRemainMoneyUseCase = GetRemainMoneyUseCase(budgetRepository, statementRepository)
	private val getSpentMoneyStatusUseCase = GetSpentMoneyStatusUseCase()

	private val job = Job()

	override fun onReceive(context: Context?, intent: Intent?) {
		super.onReceive(context, intent)
	}

	override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
		super.onUpdate(context, appWidgetManager, appWidgetIds)

		CoroutineScope(Dispatchers.Main + job).launch {
			appWidgetIds?.forEach { appWidgetId ->
				RemoteViews(context?.packageName, R.layout.appwidget_type_3).also {
					val todayBudget = getTodayBudgetUseCase.getTodayBudget()
					val remainMoney = getRemainMoneyUseCase.getRemainMoney(todayBudget)

					it.setImageViewResource(R.id.status, getStatus(todayBudget, remainMoney))
					it.setTextViewText(R.id.tv_remain_money, NumberUtils.decimalFormat.format(remainMoney))
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
			APP_WIDGET_REQUEST_CODE + appWidgetId,
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
			APP_WIDGET_REQUEST_CODE + appWidgetId,
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
