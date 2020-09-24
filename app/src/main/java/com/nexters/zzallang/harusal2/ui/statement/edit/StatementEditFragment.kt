package com.nexters.zzallang.harusal2.ui.statement.edit

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.application.util.Constants
import com.nexters.zzallang.harusal2.base.BaseFragment
import com.nexters.zzallang.harusal2.databinding.FragmentEditStatementBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

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
        viewModel.initData()
    }

    override fun init() {
        super.init()
        GlobalScope.launch {
            viewModel.setStatement(requireArguments().getLong("statementId"))
        }
    }

    override fun bindingView() {
        super.bindingView()

        if(viewModel.initType() == Constants.STATEMENT_TYPE_IN) binding.btnEditTypeIn.isChecked = true

        binding.editStatementEditAmount.onFocusChangeListener =
            View.OnFocusChangeListener { v, isFocused ->
                if(isFocused) binding.layoutStatementEditKeypad.visibility = View.VISIBLE
                else binding.layoutStatementEditKeypad.visibility = View.GONE
            }

        binding.btnGroupStatementEdit.setOnCheckedChangeListener { radioGroup, id ->
            var type = Constants.STATEMENT_TYPE_DEFALT
            when(id){
                binding.btnEditTypeOut.id -> { type = Constants.STATEMENT_TYPE_OUT }
                binding.btnEditTypeIn.id -> { type = Constants.STATEMENT_TYPE_IN }
            }
            viewModel.setType(type)
        }

        binding.btnStatementEditKeypad.setOnClickListener {
            binding.editStatementEditAmount.clearFocus()
        }

        binding.btnStatementEditDone.setOnClickListener {
            GlobalScope.launch {
                viewModel.updateStatement()
            }
            val bundle = Bundle()
            bundle.putLong("statementId", viewModel.getId())
            parentFragmentManager.beginTransaction().replace(R.id.fragment_container_statement,
                StatementDetailFragment().apply {
                    arguments = bundle
            }).commit()
        }

        val datePicker = initDatePicker()
        binding.layoutStatementEditDate.setOnClickListener {
            datePicker.show()
            datePicker.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(R.color.colorPointBlue))
            datePicker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.colorPointBlue))
        }
    }

    fun initDatePicker(): DatePickerDialog {
        val cal = Calendar.getInstance()

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, month, day ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, day)
                viewModel.setDate(SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault()).format(cal.time))
            }

        cal.time = viewModel.getInitDate()
        val datePickerDialog = DatePickerDialog(requireContext(), R.style.DialogTheme, dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
        GlobalScope.launch {
            datePickerDialog.datePicker.minDate = viewModel.getMinDate()
            datePickerDialog.datePicker.maxDate = viewModel.getMaxDate()
        }

        return datePickerDialog
    }
}