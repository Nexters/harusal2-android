package com.nexters.zzallang.harusal2.ui.statement.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseFragment
import com.nexters.zzallang.harusal2.databinding.FragmentEditStatementBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class StatementEditFragment: BaseFragment<FragmentEditStatementBinding>() {
    override fun layoutRes(): Int = R.layout.fragment_edit_statement
    override val viewModel: StatementEditViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this@StatementEditFragment
        return binding.root
    }

    override fun bindingView() {
        super.bindingView()
    }
}