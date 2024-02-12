package com.nexters.zzallang.harusal2.ui.main

import android.content.Intent
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieDrawable
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.constant.Constants
import com.nexters.zzallang.harusal2.constant.SpendState
import com.nexters.zzallang.harusal2.databinding.ActivityMainBinding
import com.nexters.zzallang.harusal2.ui.history.HistoryActivity
import com.nexters.zzallang.harusal2.ui.main.adapter.MainStatementAdapter
import com.nexters.zzallang.harusal2.ui.main.decoration.MainStatementDecoration
import com.nexters.zzallang.harusal2.ui.setting.SettingActivity
import com.nexters.zzallang.harusal2.ui.statement.register.AddStatementActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()
    private val job = Job()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = 0

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)

        initViews()
    }

    override fun onResume() {
        super.onResume()

        refreshMainData()
    }

    private fun initViews() {
        binding.rcvStatement.layoutManager = LinearLayoutManager(this)
        binding.rcvStatement.addItemDecoration(MainStatementDecoration())
        binding.rcvStatement.adapter = MainStatementAdapter()
        binding.rcvStatement.setHasFixedSize(true)
        binding.ivList.setOnClickListener {
            this.startActivity(Intent(this, HistoryActivity::class.java))
        }
        binding.btnAddStatement.setOnClickListener {
            val intent = Intent(it.context, AddStatementActivity::class.java)
            intent.putExtra(Constants.BEFORE_ACTIVITY_KEY,"MAIN")

            it.context.startActivity(intent)
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
                backgroundColor = R.color.bg_blue
                todayLivingExpensesBackgroundColor = R.color.bg_blue_multiply
                emojiName = "flex_coin.json"
                speechBubbleText = getString(R.string.text_flex)
            }
            SpendState.CLAP -> {
                backgroundColor = R.color.bg_mint
                todayLivingExpensesBackgroundColor = R.color.bg_mint_multiply
                emojiName = "clap_coin.json"
                speechBubbleText = getString(R.string.text_clap)
            }
            SpendState.DEFAULT -> {
                backgroundColor = R.color.bg_blue
                todayLivingExpensesBackgroundColor = R.color.bg_blue_multiply
                emojiName = "default_coin.json"
                speechBubbleText = getString(R.string.text_default)
            }
            SpendState.EMBARRASSED -> {
                backgroundColor = R.color.bg_yellow
                todayLivingExpensesBackgroundColor = R.color.bg_yellow_multiply
                emojiName = "embassed_coin.json"
                speechBubbleText = getString(R.string.text_embarrassed)
            }
            SpendState.CRY -> {
                backgroundColor = R.color.bg_orange
                todayLivingExpensesBackgroundColor = R.color.bg_orange_multiply
                emojiName = "cry_coin.json"
                speechBubbleText = getString(R.string.text_cry)
            }
            else -> {
                backgroundColor = R.color.bg_red
                todayLivingExpensesBackgroundColor = R.color.bg_red_multiply
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
            }
        }
    }
}
