package com.nexters.zzallang.harusal2.ui.history

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nexters.zzallang.harusal2.R
import kotlinx.android.synthetic.main.layout_history_listview.*

class HistoryMenuDialog(private var activity: Activity, private var adapter: RecyclerView.Adapter<*>) : Dialog(activity),
    View.OnClickListener {

    private var recyclerView: RecyclerView? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.layout_history_listview)

        recyclerView = recycler_view
        mLayoutManager = LinearLayoutManager(activity)
        recyclerView?.layoutManager = mLayoutManager
        recyclerView?.adapter = adapter
    }

    override fun onClick(v: View) {
    }
}