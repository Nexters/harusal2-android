package com.nexters.zzallang.harusal2.ui.statement.register

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.application.util.Constants
import com.nexters.zzallang.harusal2.application.util.IntentUtils
import com.nexters.zzallang.harusal2.base.BaseFragment
import com.nexters.zzallang.harusal2.databinding.FragmentAddMemoBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class AddMemoFragment : BaseFragment<FragmentAddMemoBinding>() {
    override fun layoutRes(): Int = R.layout.fragment_add_memo
    override val viewModel: AddMemoViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this@AddMemoFragment
        return binding.root
    }

    override fun bindingView() {
        super.bindingView()

        viewModel.setAmount(requireArguments().getString("amount") ?: "0")
        viewModel.setType(requireArguments().getInt("type"))
        viewModel.setDate(viewModel.getDateForNow())

        binding.btnStatementPre.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.btnStatementDone.setOnClickListener {
            GlobalScope.launch {
                viewModel.createStatement()
            }
            startActivity(IntentUtils.getMainActivityIntent(it.context))
        }

        val datePicker = initDatePicker()
        binding.layoutStatementDate.setOnClickListener {
            datePicker.show()
            datePicker.getButton(DatePickerDialog.BUTTON_NEGATIVE)
                .setTextColor(resources.getColor(R.color.colorPointBlue))
            datePicker.getButton(DatePickerDialog.BUTTON_POSITIVE)
                .setTextColor(resources.getColor(R.color.colorPointBlue))
        }
    }

    fun initDatePicker(): DatePickerDialog {
        val cal = Calendar.getInstance()

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, month, day ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, day)
                viewModel.setDate(
                    SimpleDateFormat(
                        Constants.DATE_FORMAT,
                        Locale.getDefault()
                    ).format(cal.time)
                )
            }

        val datePickerDialog = DatePickerDialog(
            requireContext(), R.style.DialogTheme, dateSetListener,
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