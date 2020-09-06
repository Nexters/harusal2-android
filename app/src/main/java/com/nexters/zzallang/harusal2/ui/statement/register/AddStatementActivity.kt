package com.nexters.zzallang.harusal2.ui.statement.register

import android.content.Intent
import android.os.Bundle
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivityAddStatementBinding
import com.nexters.zzallang.harusal2.ui.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddStatementActivity: BaseActivity<ActivityAddStatementBinding>() {
    override fun layoutRes(): Int = R.layout.activity_add_statement
    override val viewModel: AddStatementViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    override fun bindingView() {
        super.bindingView()
        supportFragmentManager.beginTransaction().add(R.id.fragment_container_add_statement,
            AddInputFragment()
        ).commit()

        binding.btnStatementX.setOnClickListener {
            finish()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}