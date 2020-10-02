package com.nexters.zzallang.harusal2.ui.statement.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.application.util.Constants
import com.nexters.zzallang.harusal2.base.BaseFragment
import com.nexters.zzallang.harusal2.databinding.FragmentStatementDetailBinding
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class StatementDetailFragment: BaseFragment<FragmentStatementDetailBinding>() {
    override fun layoutRes(): Int = R.layout.fragment_statement_detail
    override val viewModel: StatementDetailViewModel by viewModel()
    private val job = Job()

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

    override fun init() {
        super.init()
        GlobalScope.launch {
            viewModel.setStatement(requireArguments().getLong("statementId"))
        }
    }

    override fun bindingView() {
        super.bindingView()

        viewModel.setAmount()
        viewModel.setDate()
        viewModel.setMemo()

        binding.tvStatementType.background = when(viewModel.setType()){
            Constants.STATEMENT_TYPE_IN -> resources.getDrawable(R.drawable.bg_tag_in)
            else -> resources.getDrawable(R.drawable.bg_tag_out)
        }

        binding.btnStatementDetailEdit.setOnClickListener {
            val bundle = Bundle()
            bundle.putLong("statementId", viewModel.getId())
            parentFragmentManager.beginTransaction().replace(R.id.fragment_container_statement,
                StatementEditFragment().apply {
                    arguments = bundle
                }
            ).commit()
        }
    }
}