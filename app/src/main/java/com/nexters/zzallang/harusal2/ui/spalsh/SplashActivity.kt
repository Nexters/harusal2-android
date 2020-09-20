package com.nexters.zzallang.harusal2.ui.spalsh

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.application.AppDatabase
import com.nexters.zzallang.harusal2.ui.main.MainActivity
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
                    //TODO ON_BOARD 추가하기
                    updateOnBoardingView()
//                this.startActivity(Intent(this, OnBoardingActivity::class.java))
                } else if (!existsBudget) {
                    //TODO ON_BOARD 추가하기
//                activity = EmptyMain::class.java
                }
            }

            delay(1500L)
            job.join()
            this@SplashActivity.startActivity(Intent(this@SplashActivity, activity))
            finish()
        }
    }

    fun isFirstLoading(): Boolean {
        return sharedPref.getBoolean(FIRST_LOADING, false)
    }

    fun updateOnBoardingView() {
        with(sharedPref.edit()) {
            putBoolean(FIRST_LOADING, true)
        }
    }
}