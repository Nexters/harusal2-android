package com.nexters.zzallang.harusal2.ui.statement.edit

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.application.util.Constants
import com.nexters.zzallang.harusal2.base.BaseActivity
import com.nexters.zzallang.harusal2.databinding.ActivityEditStatementBinding
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class StatementEditActivity: BaseActivity<ActivityEditStatementBinding>() {
    override fun layoutRes(): Int = R.layout.activity_edit_statement
    override val viewModel: StatementEditViewModel by viewModel()
    private val job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    override fun bindingView() {
        super.bindingView()

        CoroutineScope(Dispatchers.Main + job).launch{
            viewModel.setStatement(intent.getLongExtra("statementId", 0L))

            viewModel.initData()

            if (viewModel.initType() == Constants.STATEMENT_TYPE_IN){
                binding.btnEditTypeIn.isChecked = true
            }

            binding.editStatementEditAmount.onFocusChangeListener =
                View.OnFocusChangeListener { v, isFocused ->
                    if (isFocused) binding.layoutStatementEditKeypad.visibility = View.VISIBLE
                    else binding.layoutStatementEditKeypad.visibility = View.GONE
                }

            binding.btnGroupStatementEdit.setOnCheckedChangeListener { radioGroup, id ->
                var type = Constants.STATEMENT_TYPE_DEFALT
                when (id) {
                    binding.btnEditTypeOut.id -> {
                        type = Constants.STATEMENT_TYPE_OUT
                    }
                    binding.btnEditTypeIn.id -> {
                        type = Constants.STATEMENT_TYPE_IN
                    }
                }
                viewModel.setType(type)
            }

            binding.btnStatementEditDone.setOnClickListener {
                GlobalScope.launch {
                    viewModel.updateStatement()
                }
                finish()
            }

            val datePicker = initDatePicker()
            binding.layoutStatementEditDate.setOnClickListener {
                datePicker.show()
                datePicker.getButton(DatePickerDialog.BUTTON_NEGATIVE)
                    .setTextColor(resources.getColor(R.color.colorPointBlue))
                datePicker.getButton(DatePickerDialog.BUTTON_POSITIVE)
                    .setTextColor(resources.getColor(R.color.colorPointBlue))
            }

            binding.btnStatementDelete.setOnClickListener {
                GlobalScope.launch {
                    viewModel.deleteStatement()
                }
                finish()
            }
        }

        binding.btnStatementEditKeypad.setOnClickListener {
            binding.editStatementEditAmount.clearFocus()
        }

        binding.btnStatementBack.setOnClickListener {
            onBackPressed()
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
        val datePickerDialog = DatePickerDialog(this, R.style.DialogTheme, dateSetListener,
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
