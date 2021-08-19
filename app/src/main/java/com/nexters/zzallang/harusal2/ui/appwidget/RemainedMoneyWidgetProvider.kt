package com.nexters.zzallang.harusal2.ui.appwidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.ui.splash.SplashActivity

class RemainedMoneyWidgetProvider : AppWidgetProvider() {
	companion object {
		const val APP_WIDGET_REQUEST_CODE = 100
	}

	override fun onReceive(context: Context?, intent: Intent?) {
		super.onReceive(context, intent)
	}

	override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
		super.onUpdate(context, appWidgetManager, appWidgetIds)
		appWidgetIds?.forEach { appWidgetId ->
			RemoteViews(context?.packageName, R.layout.appwidget_type_3).also {
				it.setImageViewResource(R.id.status, getRemainingStatus())
				it.setTextViewText(R.id.tv_remain_money, getRemainingMoney(context))
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

	private fun getRemainingStatus(): Int {
		// TODO
		val percent = 0
		return when (percent) {
			0 -> R.drawable.appwidget_status_red
			1 -> R.drawable.appwidget_status_orange
			2 -> R.drawable.appwidget_status_yellow
			3 -> R.drawable.appwidget_status_green
			else -> R.drawable.appwidget_status_blue
		}
	}

	private fun getRemainingMoney(context: Context?): String {
		// TODO
		return if (context == null) "" else String.format(context.resources.getString(R.string.appwidget_remaining_money), 9999)
	}
}