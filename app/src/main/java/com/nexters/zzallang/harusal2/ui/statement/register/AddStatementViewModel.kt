package com.nexters.zzallang.harusal2.ui.statement.register

import com.nexters.zzallang.harusal2.base.BaseViewModel

class AddStatementViewModel : BaseViewModel() {
    private var beforeActivity = ""

    fun setBeforeActivity(activityName: String) {
        beforeActivity = activityName
    }

    fun getBeforeActivity(): String {
        return this.beforeActivity
    }
}