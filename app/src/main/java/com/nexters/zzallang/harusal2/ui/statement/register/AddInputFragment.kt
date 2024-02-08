package com.nexters.zzallang.harusal2.ui.statement.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.constant.Constants
import com.nexters.zzallang.harusal2.databinding.FragmentAddInputBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddInputFragment: Fragment() {
    private lateinit var binding: FragmentAddInputBinding
    private val viewModel: AddInputViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentAddInputBinding.inflate(inflater)
        binding.vm = viewModel
        binding.lifecycleOwner = this@AddInputFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.statementAmount.observe(viewLifecycleOwner, Observer{
            if(it == ""){
                binding.tvStatementUnit.setTextColor(resources.getColor(R.color.disable_txt))
                binding.btnStatementNext.setBackgroundColor(resources.getColor(R.color.line))
                binding.btnStatementNext.isEnabled = false
            }
            else{
                binding.tvStatementUnit.setTextColor(resources.getColor(android.R.color.black))
                binding.btnStatementNext.setBackgroundColor(resources.getColor(R.color.btn_black))
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
