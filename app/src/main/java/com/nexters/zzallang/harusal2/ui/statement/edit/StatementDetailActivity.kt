package com.nexters.zzallang.harusal2.ui.statement.edit

import android.content.Intent
import android.os.Bundle
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.application.util.Constants
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivityStatementDetailBinding
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class StatementDetailActivity: BaseActivity<ActivityStatementDetailBinding>() {
    override fun layoutRes(): Int = R.layout.activity_statement_detail
    override val viewModel: StatementDetailViewModel by viewModel()
    private val job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    override fun onResume() {
        super.onResume()
        updateStatement()
    }

    override fun bindingView() {
        super.bindingView()

        updateStatement()

        binding.btnStatementDetailEdit.setOnClickListener {
            val intent = Intent(it.context, StatementEditActivity::class.java)
            intent.putExtra("statementId", viewModel.getId())
            it.context.startActivity(intent)
        }

        binding.btnStatementBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnStatementDelete.setOnClickListener {
            GlobalScope.launch {
                viewModel.deleteStatement()
            }
            finish()
        }
    }

    fun updateStatement(){
        CoroutineScope(Dispatchers.Main + job).launch{
            viewModel.setStatement(intent.getLongExtra("statementId", 0L))
            viewModel.update()
            binding.tvStatementType.background = when(viewModel.getType()){
                Constants.STATEMENT_TYPE_IN -> resources.getDrawable(R.drawable.bg_tag_in)
                else -> resources.getDrawable(R.drawable.bg_tag_out)
            }
        }
    }
}