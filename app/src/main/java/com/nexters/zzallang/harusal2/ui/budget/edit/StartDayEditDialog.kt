package com.nexters.zzallang.harusal2.ui.budget.edit

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.nexters.zzallang.harusal2.R
import java.util.*

class StartDayEditDialog(context : Context) {
    private val dlg = Dialog(context)
    private lateinit var lblDesc : TextView
    private lateinit var btnComplete : Button
    private lateinit var btnCancel : Button
    private lateinit var listener : StartDayEditDialogListener

    fun start(content : String) {
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.dialog_edit_budget)
        dlg.setCancelable(false)

        lblDesc = dlg.findViewById(R.id.tv_content)
        lblDesc.text = content

        btnComplete = dlg.findViewById(R.id.btn_complete)
        btnComplete.text = Date().date.toString() + "일로 시작"
        btnComplete.setOnClickListener {
            listener.onCompleteClicked()
            dlg.dismiss()
        }

        btnCancel = dlg.findViewById(R.id.btn_cancel)
        btnCancel.setOnClickListener {
            dlg.dismiss()
        }

        dlg.show()
    }

    fun setOnOKClickedListener(listener: () -> Unit) {
        this.listener = object: StartDayEditDialogListener {
            override fun onCompleteClicked() {
                listener()
            }
        }
    }


    interface StartDayEditDialogListener {
        fun onCompleteClicked()
    }
}