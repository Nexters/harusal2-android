package com.nexters.zzallang.harusal2.ui.statement.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseFragment
import com.nexters.zzallang.harusal2.databinding.FragmentStatementDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class StatementDetailFragment: BaseFragment<FragmentStatementDetailBinding>() {
    override fun layoutRes(): Int = R.layout.fragment_statement_detail
    override val viewModel: StatementDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this@StatementDetailFragment
        return binding.root
    }

    override fun bindingView() {
        super.bindingView()

        // TODO : 뷰 연결하면 수정
        viewModel.setDate("2020.07.22")
        viewModel.setAmount("5400")
        viewModel.setMemo("편의점 김밥")

        binding.btnStatementDetailEdit.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("date", viewModel.getDate())
            bundle.putString("amount", viewModel.getAmount())
            bundle.putString("memo", viewModel.getMemo())
            parentFragmentManager.beginTransaction().replace(R.id.fragment_container_statement,
                StatementEditFragment().apply {
                    arguments = bundle
                }
            ).addToBackStack(null).commit()
        }
    }
}