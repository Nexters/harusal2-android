package com.nexters.zzallang.harusal2.ui.statement

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseFragment
import com.nexters.zzallang.harusal2.databinding.FragmentAddInputBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.lifecycle.Observer

class AddInputFragment: BaseFragment<FragmentAddInputBinding>() {
    override fun layoutRes(): Int = R.layout.fragment_add_input
    override val viewModel: AddInputViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this@AddInputFragment
        return binding.root
    }

    override fun bindingView() {
        super.bindingView()

        viewModel.statementAmount.observe(viewLifecycleOwner, Observer{
            if(it == "") binding.tvStatementUnit.setTextColor(resources.getColor(R.color.colorGray))
            else binding.tvStatementUnit.setTextColor(resources.getColor(android.R.color.black))
            viewModel.updateConvertedAmount(it)
        })

        binding.btnStatementNext.setOnClickListener {
            setFragmentResult("statement_data_passing", bundleOf("amount" to viewModel.statementAmount.value.toString()))
            setFragmentResult("statement_data_passing", bundleOf("type" to viewModel.getType()))
            parentFragmentManager.beginTransaction().replace(R.id.fragment_container_add_statement, AddMemoFragment()).addToBackStack(null).commit()
        }
    }
}