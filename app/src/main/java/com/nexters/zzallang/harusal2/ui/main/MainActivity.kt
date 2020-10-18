package com.nexters.zzallang.harusal2.ui.main

import android.content.Intent
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
import com.nexters.zzallang.harusal2.ui.history.HistoryActivity
import com.nexters.zzallang.harusal2.ui.main.adapter.MainStatementAdapter
import com.nexters.zzallang.harusal2.ui.main.decoration.MainStatementDecoration
import com.nexters.zzallang.harusal2.ui.setting.SettingActivity
import com.nexters.zzallang.harusal2.ui.statement.register.AddStatementActivity
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
    }

    override fun onResume() {
        super.onResume()

        refreshMainData()
    }

    override fun bindingView() {
        super.bindingView()

        binding.rcvStatement.layoutManager = LinearLayoutManager(this)
        binding.rcvStatement.addItemDecoration(MainStatementDecoration())
        binding.rcvStatement.adapter = MainStatementAdapter()
        binding.rcvStatement.setHasFixedSize(true)
        binding.ivList.setOnClickListener {
            this.startActivity(Intent(this, HistoryActivity::class.java))
        }
        binding.btnAddStatement.setOnClickListener {
            this.startActivity(Intent(this, AddStatementActivity::class.java))
        }
        binding.ivMenu.setOnClickListener {
            this.startActivity(Intent(this, SettingActivity::class.java))
        }
    }

    private fun setSpendStateViews(state: SpendState) {
        val backgroundColor: Int
        val todayLivingExpensesBackgroundColor: Int
        val emojiName: String
        val speechBubbleText: String

        when (state) {
            SpendState.FLEX -> {
                backgroundColor = R.color.colorPointBlueBackground
                todayLivingExpensesBackgroundColor = R.color.colorPointBlue
                emojiName = "flex_coin.json"
                speechBubbleText = getString(R.string.text_flex)
            }
            SpendState.CLAP -> {
                backgroundColor = R.color.colorPointGreenBackground
                todayLivingExpensesBackgroundColor = R.color.colorPointGreen
                emojiName = "clap_coin.json"
                speechBubbleText = getString(R.string.text_clap)
            }
            SpendState.DEFAULT -> {
                backgroundColor = R.color.colorPointBlueBackground
                todayLivingExpensesBackgroundColor = R.color.colorPointBlue
                emojiName = "default_coin.json"
                speechBubbleText = getString(R.string.text_default)
            }
            SpendState.EMBARRASSED -> {
                backgroundColor = R.color.colorPointYellowBackground
                todayLivingExpensesBackgroundColor = R.color.colorPointYellow
                emojiName = "embassed_coin.json"
                speechBubbleText = getString(R.string.text_embarrassed)
            }
            SpendState.CRY -> {
                backgroundColor = R.color.colorPointOrangeBackground
                todayLivingExpensesBackgroundColor = R.color.colorPointOrange
                emojiName = "cry_coin.json"
                speechBubbleText = getString(R.string.text_cry)
            }
            else -> {
                backgroundColor = R.color.colorPointRedBackground
                todayLivingExpensesBackgroundColor = R.color.colorPointRed
                emojiName = "volcano_coin_draft2.json"
                speechBubbleText = getString(R.string.text_volcano)
            }
        }

        binding.tvSpeechBubble.text = speechBubbleText

        binding.ivEmoji.setAnimation(emojiName)
        binding.ivEmoji.playAnimation()
        binding.ivEmoji.repeatCount = LottieDrawable.INFINITE

        val color = resources.getColor(backgroundColor, null)
        binding.layoutWhole.setBackgroundColor(color)
        binding.layoutAppbar.setBackgroundColor(color)

        val layoutTodayMoneyDrawable: Drawable = binding.layoutTodayMoney.background
        if (layoutTodayMoneyDrawable is GradientDrawable) {
            layoutTodayMoneyDrawable.setColor(
                ContextCompat.getColor(
                    this,
                    todayLivingExpensesBackgroundColor
                )
            )
        }

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }

    private fun refreshMainData() {
        CoroutineScope(Dispatchers.Main + job).launch {
            val spendState = viewModel.checkCurrentSpendState()
            setSpendStateViews(spendState)

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