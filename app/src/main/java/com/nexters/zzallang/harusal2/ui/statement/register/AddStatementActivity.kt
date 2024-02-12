package com.nexters.zzallang.harusal2.ui.statement.register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.constant.Constants
import com.nexters.zzallang.harusal2.databinding.ActivityAddStatementBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddStatementActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddStatementBinding
    private val viewModel: AddStatementViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStatementBinding.inflate(layoutInflater)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
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
