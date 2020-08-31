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

        viewModel.setStatement(requireArguments().getLong("id"))

        binding.tvStatementDate.text = viewModel.getDate()
        binding.tvStatementAmount.text = viewModel.getAmount()
        binding.tvStatementMemo.text = viewModel.getMemo()

        binding.btnStatementDetailEdit.setOnClickListener {
            val bundle = Bundle()
            bundle.putLong("id", viewModel.getId())
            parentFragmentManager.beginTransaction().replace(R.id.fragment_container_statement,
                StatementEditFragment().apply {
                    arguments = bundle
                }
            ).addToBackStack(null).commit()
        }
    }
}