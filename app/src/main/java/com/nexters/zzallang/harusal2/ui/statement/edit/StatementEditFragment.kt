package com.nexters.zzallang.harusal2.ui.statement.edit

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.application.util.Constants
import com.nexters.zzallang.harusal2.base.BaseFragment
import com.nexters.zzallang.harusal2.databinding.FragmentEditStatementBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.ext.scope

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

    override fun onResume() {
        super.onResume()

        bindingEditDefault()
    }

    override fun bindingView() {
        super.bindingView()

        binding.editStatementEditAmount.onFocusChangeListener =
            View.OnFocusChangeListener { v, isFocused ->
                if(isFocused) binding.layoutStatementEditKeypad.visibility = View.VISIBLE
                else binding.layoutStatementEditKeypad.visibility = View.GONE
            }

        binding.btnGroupStatementEdit.setOnCheckedChangeListener { radioGroup, id ->
            var type = Constants.STATEMENT_TYPE_DEFALT
            when(id){
                binding.btnEditTypeOut.id -> {
                    type = Constants.STATEMENT_TYPE_OUT
                }
                binding.btnEditTypeIn.id -> {
                    type = Constants.STATEMENT_TYPE_IN
                }
            }
            viewModel.setType(type)
        }

        binding.btnStatementEditKeypad.setOnClickListener {
            binding.editStatementEditAmount.clearFocus()
        }

        binding.btnStatementEditDone.setOnClickListener {
            Log.e("check log", "what..")
            GlobalScope.launch {
                viewModel.updateStatement()
            }
        }
    }

    fun bindingEditDefault(){
        binding.tvStatementEditDate.text = requireArguments().getString("date")
        binding.editStatementEditAmount.setText(requireArguments().getString("amount"))
        binding.editStatementEditMemo.setText(requireArguments().getString("memo"))
    }
}