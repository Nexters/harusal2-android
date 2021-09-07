package com.nexters.zzallang.harusal2.ui.statement.register

import android.os.Bundle
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.constant.Constants
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivityAddStatementBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddStatementActivity : BaseActivity<ActivityAddStatementBinding>() {
    override fun layoutRes(): Int = R.layout.activity_add_statement
    override val viewModel: AddStatementViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    override fun bindingView() {
        super.bindingView()
        supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container_add_statement,
                        AddInputFragment()
                ).commit()

        binding.btnStatementX.setOnClickListener {
            finish()
        }
        viewModel.setBeforeActivity(intent.getStringExtra(Constants.BEFORE_ACTIVITY_KEY)?:"EMPTY")
    }

}