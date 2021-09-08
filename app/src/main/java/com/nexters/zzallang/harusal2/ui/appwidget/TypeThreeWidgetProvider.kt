package com.nexters.zzallang.harusal2.ui.appwidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.content.ContextCompat
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
class TypeThreeWidgetProvider : AppWidgetProvider(), KoinComponent {
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

		if (context == null) return

		CoroutineScope(Dispatchers.Main + job).launch {
			appWidgetIds?.forEach { appWidgetId ->
				RemoteViews(context.packageName, R.layout.appwidget_type_three_layout).also {

					val todayBudget = getTodayBudgetUseCase.getTodayBudget()
					val remainMoney = getRemainMoneyUseCase.getRemainMoney(todayBudget)

					it.updateStatusView(context, todayBudget, remainMoney)
					it.updateRemainTextView(context, remainMoney)

					it.setOnClickRefresh(context, appWidgetId)
					it.setOnClickOpenApp(context, appWidgetId)

					appWidgetManager?.updateAppWidget(appWidgetId, it)
				}
			}
		}
	}

	private fun RemoteViews.updateStatusView(context: Context, todayBudget: Int, remainMoney: Int) {
		val spentMoneyStatus = getSpentMoneyStatusUseCase.getSpentMoneyStatus(todayBudget, remainMoney)
		val color = ContextCompat.getColor(context, SpendState.getBackgroundColor(spentMoneyStatus))

		setInt(R.id.spent_money_status, "setColorFilter", color)
	}

	private fun RemoteViews.updateRemainTextView(context: Context, remainMoney: Int) {
		val remainMoneyText = context.resources?.getString(R.string.appwidget_remaining_money, NumberUtils.decimalFormat.format(remainMoney)).orEmpty()
		setTextViewText(R.id.remain_money, remainMoneyText)
	}

	private fun RemoteViews.setOnClickOpenApp(context: Context, appWidgetId: Int) {
		val pendingIntent = PendingIntent.getActivity(
			context,
			THREE_REQUEST_CODE + appWidgetId,
			Intent(context, SplashActivity::class.java),
			PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
		)

		setOnClickPendingIntent(R.id.root, pendingIntent)
	}

	private fun RemoteViews.setOnClickRefresh(context: Context, appWidgetId: Int) {
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

	override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
		job.cancel()
		super.onDeleted(context, appWidgetIds)
	}
}
