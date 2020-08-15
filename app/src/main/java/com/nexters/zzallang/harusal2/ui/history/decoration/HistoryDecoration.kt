package com.nexters.zzallang.harusal2.ui.history.decoration

import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nexters.zzallang.harusal2.application.App

class HistoryDecoration: RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val dp = when(parent.getChildAdapterPosition(view)){
            0 -> 20F
            1, 3 -> 10F
            2 -> 40F
            else -> 12F
        }

        outRect.top = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp, App.app.resources.displayMetrics
        ).toInt()
    }
}