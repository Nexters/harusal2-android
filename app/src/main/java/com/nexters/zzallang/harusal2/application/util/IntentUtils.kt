package com.nexters.zzallang.harusal2.application.util

import android.content.Context
import android.content.Intent
import com.nexters.zzallang.harusal2.ui.main.EmptyMainActivity
import com.nexters.zzallang.harusal2.ui.main.MainActivity

object IntentUtils {
    fun getMainActivityIntent(context: Context): Intent {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        return intent
    }

    fun getEmptyMainActivityIntent(context: Context): Intent {
        val intent = Intent(context, EmptyMainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        return intent
    }
}