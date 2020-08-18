package com.nexters.zzallang.harusal2.ui.statement.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.application.App
import com.nexters.zzallang.harusal2.base.BaseFragment
import com.nexters.zzallang.harusal2.databinding.FragmentAddMemoBinding
import com.nexters.zzallang.harusal2.ui.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddMemoFragment: BaseFragment<FragmentAddMemoBinding>() {
    override fun layoutRes(): Int = R.layout.fragment_add_memo
    override val viewModel: AddMemoViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this@AddMemoFragment
        return binding.root
    }

    override fun bindingView() {
        super.bindingView()

        // TODO amount get..null.. whyrano..
        setFragmentResultListener("statement_data_passing"){ _, bundle ->
            viewModel.setAmount(bundle.getString("amount", "0"))
            viewModel.setType(bundle.getInt("type"))
        }

        binding.btnStatementPre.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.btnStatementDone.setOnClickListener {
            // TODO createStatement 구현 후 주석 해제하기
            //viewModel.createStatement()
            val intent = Intent()
            intent.setClass(App.app, MainActivity::class.java)
            startActivity(intent)
        }
    }

}