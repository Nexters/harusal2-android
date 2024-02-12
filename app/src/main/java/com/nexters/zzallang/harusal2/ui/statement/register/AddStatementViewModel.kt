package com.nexters.zzallang.harusal2.ui.statement.register

import androidx.lifecycle.ViewModel

class AddStatementViewModel : ViewModel() {
    private var beforeActivity = ""

    fun setBeforeActivity(activityName: String) {
        beforeActivity = activityName
    }

    fun getBeforeActivity(): String {
        return this.beforeActivity
    }
}
