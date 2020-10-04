package com.nexters.zzallang.harusal2.ui.splash

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.application.AppDatabase
import com.nexters.zzallang.harusal2.ui.main.EmptyMainActivity
import com.nexters.zzallang.harusal2.ui.main.MainActivity
import com.nexters.zzallang.harusal2.ui.onBoarding.OnBoardingActivity
import kotlinx.coroutines.*

class SplashActivity : Activity() {
    private lateinit var sharedPref: SharedPreferences

    companion object {
        const val FIRST_LOADING = "first_loading_preferences"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        sharedPref = this.getPreferences(MODE_PRIVATE)
        startLoading()
    }

    private fun startLoading() {
        CoroutineScope(Dispatchers.IO).launch {
            var activity: Class<*> = MainActivity::class.java
            var existsBudget: Boolean

            val job = launch {
                withContext(Dispatchers.Default) {
                    existsBudget = AppDatabase.instance.budgetDao().existsBudget()
                }
                if (isFirstLoading()) {
                    updateOnBoardingView()
                    activity = OnBoardingActivity::class.java
                } else if (!existsBudget) {
                    activity = EmptyMainActivity::class.java
                }
            }

            delay(1500L)
            job.join()
            this@SplashActivity.startActivity(Intent(this@SplashActivity, activity))
            finish()
        }
    }

    private fun isFirstLoading(): Boolean {
        return sharedPref.getBoolean(FIRST_LOADING, true)
    }

    private fun updateOnBoardingView() {
        sharedPref.edit().putBoolean(FIRST_LOADING, false).apply()
    }
}