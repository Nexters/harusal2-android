package com.nexters.zzallang.harusal2.ui.history.menu

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ListView
import com.nexters.zzallang.harusal2.R

class HistoryMenuDialog(private var activity: Activity, private var adapter: HistoryMenuAdapter) :
    Dialog(activity),
    View.OnClickListener {

    private var listView: ListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.layout_history_listview)

        listView = findViewById(R.id.listview_menu)
        listView?.adapter = adapter
    }

    override fun onClick(v: View) {
    }

    interface ItemClickListener {
        fun clickOnItem(position: Int)
    }
}
