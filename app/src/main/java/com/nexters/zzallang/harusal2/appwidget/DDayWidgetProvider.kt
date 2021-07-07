package com.nexters.zzallang.harusal2.appwidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.nexters.zzallang.harusal2.R

class DDayWidgetProvider : AppWidgetProvider() {

	override fun onReceive(context: Context?, intent: Intent?) {
		super.onReceive(context, intent)
	}

	override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
		super.onUpdate(context, appWidgetManager, appWidgetIds)

		appWidgetIds?.forEach { appWidgetId ->
			RemoteViews(context?.packageName, R.layout.appwidget_d_day).also {
				it.setTextView()
				appWidgetManager?.updateAppWidget(appWidgetId, it)
			}
		}
	}

	private fun RemoteViews.setTextView() {
		setTextViewText(R.id.dday_date, "D-21")
		setTextViewText(R.id.dday_title, "7월 남은 예산")
		setTextViewText(R.id.dday_budget, "200,000")
	}
}