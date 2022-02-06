package com.nexters.zzallang.harusal2.ui.appWidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.application.util.AppWidgetToastUtil
import com.nexters.zzallang.harusal2.application.util.NumberUtils
import com.nexters.zzallang.harusal2.constant.Actions
import com.nexters.zzallang.harusal2.ui.splash.SplashActivity
import com.nexters.zzallang.harusal2.usecase.GetRemainDayUseCase
import com.nexters.zzallang.harusal2.usecase.GetRemainMoneyUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class MonthlyCombinationWidgetProvider : AppWidgetProvider(), KoinComponent {
	companion object {
		const val MONTHLY_COMBINATION_REQUEST_CODE = 105
	}

	private val job = Job()

	private val getRemainDayUseCase: GetRemainDayUseCase by inject()
	private val getRemainMoneyUseCase: GetRemainMoneyUseCase by inject()

	override fun onReceive(context: Context?, intent: Intent?) {
		super.onReceive(context, intent)
		if (intent == null || context == null) return

		val ids = AppWidgetManager.getInstance(context).getAppWidgetIds(ComponentName(context, MonthlyCombinationWidgetProvider::class.java))
		val widget = MonthlyCombinationWidgetProvider()

		if (intent.action.equals(Actions.ACTION_REFRESH)) {
			AppWidgetToastUtil.showToast(context)
			widget.onUpdate(context, AppWidgetManager.getInstance(context), ids)
		}
	}

	override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
		super.onUpdate(context, appWidgetManager, appWidgetIds)

		CoroutineScope(Dispatchers.Main + job).launch {
			appWidgetIds?.forEach { appWidgetId ->
				RemoteViews(context?.packageName, R.layout.appwidget_type_five_layout).also {
					val remainMoney = getRemainMoneyUseCase.getRemainMoney()

					val remainDayText = context?.resources?.getString(R.string.appwidget_d_day, getRemainDayUseCase.getRemainDay()).orEmpty()
					val remainMoneyText = context?.resources?.getString(R.string.appwidget_remaining_money, NumberUtils.decimalFormat.format(remainMoney)).orEmpty()

					it.setTextViewText(R.id.remain_day, remainDayText)
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
			MONTHLY_COMBINATION_REQUEST_CODE + appWidgetId,
			Intent(context, SplashActivity::class.java),
			PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
		)

		setOnClickPendingIntent(R.id.root, pendingIntent)
	}

	private fun RemoteViews.setOnClickRefresh(context: Context?, appWidgetId: Int) {
		val intent = Intent(context, MonthlyCombinationWidgetProvider::class.java).apply {
			action = Actions.ACTION_REFRESH
			putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, intArrayOf(appWidgetId))
		}

		val pendingIntent = PendingIntent.getBroadcast(
			context,
			MONTHLY_COMBINATION_REQUEST_CODE + appWidgetId,
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
