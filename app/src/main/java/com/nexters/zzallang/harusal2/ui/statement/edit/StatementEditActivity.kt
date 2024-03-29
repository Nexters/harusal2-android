package com.nexters.zzallang.harusal2.ui.statement.edit

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.constant.Constants
import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.databinding.ActivityEditStatementBinding
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.util.*

class StatementEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditStatementBinding
    private val viewModel: StatementEditViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditStatementBinding.inflate(layoutInflater)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        lifecycleScope.launch {
            viewModel.setStatement(intent.getLongExtra("statementId", 0L))

            viewModel.initData()

            if (viewModel.initType() == Constants.STATEMENT_TYPE_IN) {
                binding.btnEditTypeIn.isChecked = true
            }

            viewModel.statementAmount.observe(this@StatementEditActivity, Observer{
                if(it == ""){
                    binding.btnStatementEditKeypad.setBackgroundColor(resources.getColor(R.color.line))
                    binding.btnStatementEditKeypad.isEnabled = false
                }else{
                    binding.btnStatementEditKeypad.setBackgroundColor(resources.getColor(R.color.btn_black))
                    binding.btnStatementEditKeypad.isEnabled = true
                }
            })

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
                lifecycleScope.launch {
                    viewModel.updateStatement()
                }
                finish()
            }

            val datePicker = initDatePicker()
            binding.layoutStatementEditDate.setOnClickListener {
                datePicker.show()
                datePicker.getButton(DatePickerDialog.BUTTON_NEGATIVE)
                    .setTextColor(resources.getColor(R.color.bg_blue_multiply))
                datePicker.getButton(DatePickerDialog.BUTTON_POSITIVE)
                    .setTextColor(resources.getColor(R.color.bg_blue_multiply))
            }

            binding.btnStatementDelete.setOnClickListener {
                lifecycleScope.launch {
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

    private fun initDatePicker(): DatePickerDialog {
        var todayDate = LocalDate.now()
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, month, day ->
                todayDate = todayDate.withYear(year)
                    .withMonth(month + 1)
                    .withDayOfMonth(day)

                viewModel.setDate(DateUtils.toString(todayDate, Constants.DATE_FORMAT))
            }

        val datePickerDialog = DatePickerDialog(
            this,
            R.style.DialogTheme,
            dateSetListener,
            todayDate.year,
            todayDate.monthValue - 1,
            todayDate.dayOfMonth
        )

        lifecycleScope.launch {
            datePickerDialog.datePicker.minDate = viewModel.getMinDate()
            datePickerDialog.datePicker.maxDate = viewModel.getMaxDate()
        }

        return datePickerDialog
    }
}
