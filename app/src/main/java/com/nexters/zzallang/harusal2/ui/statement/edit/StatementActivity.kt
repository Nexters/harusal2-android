package com.nexters.zzallang.harusal2.ui.statement.edit

import android.os.Bundle
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivityStatementBinding
import com.nexters.zzallang.harusal2.ui.statement.register.AddInputFragment
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

        supportFragmentManager.beginTransaction().add(R.id.fragment_container_statement,
            StatementDetailFragment()
        ).commit()

        binding.btnStatementBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnStatementDelete.setOnClickListener {
            GlobalScope.launch {
                // TODO : 뷰 연결하면 id 받아와서 붙이기
                viewModel.deleteStatement(0L)
            }
        }
    }

}