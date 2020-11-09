package com.nexters.zzallang.harusal2.ui.statement.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.base.BaseFragment
import com.nexters.zzallang.harusal2.databinding.FragmentAddInputBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.lifecycle.Observer
import com.nexters.zzallang.harusal2.application.util.Constants

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
            if(it == ""){
                binding.tvStatementUnit.setTextColor(resources.getColor(R.color.colorGray))
                binding.btnStatementNext.setBackgroundColor(resources.getColor(R.color.colorPointDefault))
                binding.btnStatementNext.isEnabled = false
            }
            else{
                binding.tvStatementUnit.setTextColor(resources.getColor(android.R.color.black))
                binding.btnStatementNext.setBackgroundColor(resources.getColor(R.color.colorBtnBlack))
                binding.btnStatementNext.isEnabled = true
            }
            viewModel.updateConvertedAmount(it)
        })

        binding.statementType.setOnCheckedChangeListener { radioGroup, id ->
            var type = Constants.STATEMENT_TYPE_DEFALT
            when(id){
                binding.btnStatementOut.id -> {
                    type = Constants.STATEMENT_TYPE_OUT
                }
                binding.btnStatementIn.id -> {
                    type = Constants.STATEMENT_TYPE_IN
                }
            }
            viewModel.setType(type)
        }

        binding.btnStatementNext.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("type", viewModel.getType())
            if (viewModel.statementAmount.value == "") bundle.putString("amount", "0")
            else bundle.putString("amount", viewModel.statementAmount.value)
            parentFragmentManager.beginTransaction().replace(R.id.fragment_container_add_statement, AddMemoFragment().apply {
                arguments = bundle
            }).addToBackStack(null).commit()
        }
    }
}