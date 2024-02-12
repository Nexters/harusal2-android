package com.nexters.zzallang.harusal2.ui.statement.edit

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.constant.Constants
import com.nexters.zzallang.harusal2.databinding.ActivityStatementDetailBinding
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class StatementDetailActivity: AppCompatActivity() {
    private lateinit var binding: ActivityStatementDetailBinding
    private val viewModel: StatementDetailViewModel by viewModel()
    private val job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatementDetailBinding.inflate(layoutInflater)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)

        initViews()
    }

    override fun onResume() {
        super.onResume()
        updateStatement()
    }

    private fun initViews() {
        binding.btnStatementDetailEdit.setOnClickListener {
            val intent = Intent(it.context, StatementEditActivity::class.java)
            intent.putExtra("statementId", viewModel.getId())
            it.context.startActivity(intent)
        }

        binding.btnStatementBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnStatementDelete.setOnClickListener {
            lifecycleScope.launch {
                viewModel.deleteStatement()
            }
            finish()
        }
    }

    private fun updateStatement(){
        lifecycleScope.launch{
            viewModel.setStatement(intent.getLongExtra("statementId", 0L))
            viewModel.update()
            binding.tvStatementType.background = when(viewModel.getType()){
                Constants.STATEMENT_TYPE_IN -> resources.getDrawable(R.drawable.bg_tag_in)
                else -> resources.getDrawable(R.drawable.bg_tag_out)
            }
        }
    }
}
