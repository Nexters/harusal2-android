package com.nexters.zzallang.harusal2.ui.appwidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.application.util.NumberUtils
import com.nexters.zzallang.harusal2.data.repository.BudgetRepository
import com.nexters.zzallang.harusal2.data.repository.StatementRepository
import com.nexters.zzallang.harusal2.ui.splash.SplashActivity
import com.nexters.zzallang.harusal2.usecase.BudgetUseCase
import com.nexters.zzallang.harusal2.usecase.StatementUseCase
import kotlinx.coroutines.*
import java.time.LocalDate

class RemainedMoneyWidgetProvider : AppWidgetProvider() {
	companion object {
		const val APP_WIDGET_REQUEST_CODE = 100
	}

	private val job = Job()
	private val statementUseCase = StatementUseCase(StatementRepository())
	private val budgetUseCase = BudgetUseCase(BudgetRepository())

	private var livingExpenses = 0
	private var remainMoney = 0

	override fun onReceive(context: Context?, intent: Intent?) {
		super.onReceive(context, intent)
	}

	override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
		super.onUpdate(context, appWidgetManager, appWidgetIds)

		appWidgetIds?.forEach { appWidgetId ->
			RemoteViews(context?.packageName, R.layout.appwidget_type_3).also {
				initData(context, appWidgetId, it)

				it.setImageViewResource(R.id.status, getStatus())
				it.setTextViewText(R.id.tv_remain_money, NumberUtils.decimalFormat.format(livingExpenses))
				it.setOnClickRefresh(context, appWidgetId)
				it.setOnClickOpenApp(context, appWidgetId)

				appWidgetManager?.updateAppWidget(appWidgetId, it)
			}
		}
	}

	private fun RemoteViews.setOnClickOpenApp(context: Context?, appWidgetId: Int) {
		val pendingIntent = PendingIntent.getActivity(
			context,
			APP_WIDGET_REQUEST_CODE + appWidgetId,
			Intent(context, SplashActivity::class.java),
			PendingIntent.FLAG_UPDATE_CURRENT
		)

		setOnClickPendingIntent(R.id.root, pendingIntent)
	}

	private fun RemoteViews.setOnClickRefresh(context: Context?, appWidgetId: Int) {
		val intent = Intent(context, RemainedMoneyWidgetProvider::class.java).apply {
			action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
			putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, intArrayOf(appWidgetId))
		}

		val pendingIntent = PendingIntent.getBroadcast(
			context,
			APP_WIDGET_REQUEST_CODE + appWidgetId,
			intent,
			PendingIntent.FLAG_UPDATE_CURRENT
		)

		setOnClickPendingIntent(R.id.refresh, pendingIntent)
	}

	private fun getStatus() = when {
		(livingExpenses * (7f / 10)) < remainMoney -> {
			R.drawable.appwidget_status_blue
		}
		(livingExpenses * (1f / 10)) < remainMoney -> {
			R.drawable.appwidget_status_green
		}
		(livingExpenses * (4f / 10)) < remainMoney -> {
			R.drawable.appwidget_status_yellow
		}
		(livingExpenses * (8f / 10)) < remainMoney -> {
			R.drawable.appwidget_status_orange
		}
		else -> {
			R.drawable.appwidget_status_red
		}
	}

	private fun initData(context: Context?, appWidgetId: Int, remoteViews: RemoteViews) {
		CoroutineScope(Dispatchers.IO + job).launch {
			var spentMoney = 0
			val now = LocalDate.now()

			val budget = withContext(Dispatchers.IO + job) {
				budgetUseCase.findRecentBudget()
			}

			val statements = withContext(Dispatchers.IO + job) {
				statementUseCase.findStatementByBudgetId(budget.id)
			}

			statements.filter { it.date.isBefore(now) }.forEach { spentMoney += it.amount }

			val remainDate = DateUtils.calculateDate(now, budget.endDate)

			livingExpenses = (budget.budget + spentMoney) / remainDate

			val todayStatements = statements.filter { statement -> statement.date.dayOfMonth == LocalDate.now().dayOfMonth }

			var tempMoney = livingExpenses
			for (item in todayStatements) {
				tempMoney += item.amount
			}

			remainMoney = tempMoney

			AppWidgetManager.getInstance(context)?.updateAppWidget(appWidgetId, remoteViews)
		}
	}

	override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
		job.cancel()
		super.onDeleted(context, appWidgetIds)
	}
}