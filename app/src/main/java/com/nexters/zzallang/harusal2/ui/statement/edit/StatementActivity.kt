package com.nexters.zzallang.harusal2.ui.statement.edit

import android.os.Bundle
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivityStatementBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class StatementActivity: BaseActivity<ActivityStatementBinding>(){
    override fun layoutRes(): Int = R.layout.activity_statement
    override val viewModel: StatementViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    override fun bindingView() {
        super.bindingView()

        viewModel.setId(this.intent.getLongExtra("statementId", 0L))

        val bundle = Bundle()
        bundle.putLong("statementId", viewModel.getId())
        supportFragmentManager.beginTransaction().add(R.id.fragment_container_statement,
            StatementDetailFragment().apply {
                arguments = bundle
            }
        ).commit()

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

}