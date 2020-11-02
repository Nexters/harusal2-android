package com.nexters.zzallang.harusal2.ui.main.decoration

import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nexters.zzallang.harusal2.application.App


class MainStatementDecoration: RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)

        if (position == 0) {
            outRect.top = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 20F, App.app.resources.displayMetrics
            ).toInt()
        }
        if (position != 0) {
            outRect.top = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 8F, App.app.resources.displayMetrics
            ).toInt()
        }
        if ((position + 1) == state.itemCount) {
            outRect.bottom = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 20F, App.app.resources.displayMetrics
            ).toInt()
        }

        outRect.left = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 20F, App.app.resources.displayMetrics
        ).toInt()

        outRect.right = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 20F, App.app.resources.displayMetrics
        ).toInt()
    }
}