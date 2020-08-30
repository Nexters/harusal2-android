package com.nexters.zzallang.harusal2.ui.main

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieDrawable
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivityMainBinding
import com.nexters.zzallang.harusal2.ui.main.adapter.MainStatementAdapter
import com.nexters.zzallang.harusal2.ui.main.decoration.MainStatementDecoration
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun layoutRes(): Int = R.layout.activity_main
    override val viewModel: MainViewModel by viewModel()
    private val job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        refreshMainData()
    }

    override fun bindingView() {
        super.bindingView()

        binding.ivEmoji.playAnimation()
        binding.ivEmoji.repeatCount = LottieDrawable.INFINITE

        binding.rcvStatement.layoutManager = LinearLayoutManager(this)
        binding.rcvStatement.addItemDecoration(MainStatementDecoration())
        binding.rcvStatement.adapter = MainStatementAdapter()
        binding.rcvStatement.setHasFixedSize(true)
    }

    private fun setBackgroundColor(state: SpendState) {
        val backgroundColor: Int
        val todayLivingExpensesBackgroundColor: Int

        when (state) {
            SpendState.EXCELLENT -> {
                backgroundColor = R.color.colorPointBlueBackground
                todayLivingExpensesBackgroundColor = R.color.colorPointBlue
            }
            SpendState.GREAT -> {
                backgroundColor = R.color.colorPointGreenBackground
                todayLivingExpensesBackgroundColor = R.color.colorPointGreen
            }
            SpendState.GOOD -> {
                backgroundColor = R.color.colorPointYellowBackground
                todayLivingExpensesBackgroundColor = R.color.colorPointYellow
            }
            SpendState.BAD -> {
                backgroundColor = R.color.colorPointOrangeBackground
                todayLivingExpensesBackgroundColor = R.color.colorPointOrange
            }
            else -> {
                backgroundColor = R.color.colorPointRedBackground
                todayLivingExpensesBackgroundColor = R.color.colorPointRed
            }
        }

        val color = resources.getColor(backgroundColor, null)
        binding.layoutWhole.setBackgroundColor(color)
        binding.layoutAppbar.setBackgroundColor(color)

        val layoutTodayMoneyDrawable: Drawable = binding.layoutTodayMoney.background
        if (layoutTodayMoneyDrawable is GradientDrawable) {
            layoutTodayMoneyDrawable.setColor(ContextCompat.getColor(this, todayLivingExpensesBackgroundColor))
        }

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }

    private fun refreshMainData() {
        CoroutineScope(Dispatchers.Main + job).launch {
            val spendState = viewModel.checkCurrentSpendState()
            setBackgroundColor(spendState)

            with(binding.rcvStatement.adapter as MainStatementAdapter) {
                clearMainStatementList()
                val todaySpendStatementList = viewModel.getTodaySpendStatementList()
                addStatements(todaySpendStatementList)

                /* TODO: AppbarLayout의 스크롤을 막는 방법을 알게되면 활성화하여 같이 사용하도록 하자 */
//                binding.rcvStatement.isNestedScrollingEnabled = todaySpendStatementList.isNotEmpty()
            }
        }
    }
}