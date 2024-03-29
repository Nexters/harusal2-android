package com.nexters.zzallang.harusal2.ui.statement.register

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.constant.Constants
import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.databinding.FragmentAddMemoBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate

class AddMemoFragment : Fragment() {
    private lateinit var binding: FragmentAddMemoBinding
    private val viewModel: AddMemoViewModel by viewModel()
    private val addStatementViewModel: AddStatementViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentAddMemoBinding.inflate(inflater, container, false)
        binding.vm = viewModel
        binding.lifecycleOwner = this@AddMemoFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setAmount(requireArguments().getString("amount") ?: "0")
        viewModel.setType(requireArguments().getInt("type"))

        if("MAIN" == addStatementViewModel.getBeforeActivity()){
            viewModel.setDate(viewModel.getDateForNow())
        }

        viewModel.stateDate.observe(viewLifecycleOwner) {
            if (it == "") {
                binding.btnStatementDone.setBackgroundColor(resources.getColor(R.color.line))
                binding.btnStatementDone.isEnabled = false
            } else {
                binding.btnStatementDone.setBackgroundColor(resources.getColor(R.color.point_color))
                binding.btnStatementDone.isEnabled = true
            }
        }

        binding.btnStatementPre.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.btnStatementDone.setOnClickListener {
            lifecycleScope.launch {
                viewModel.createStatement()
            }

            activity?.supportFragmentManager
                    ?.beginTransaction()
                    ?.remove(this)
                    ?.commit()

            activity?.finish()
        }

        val datePicker = initDatePicker()
        binding.layoutStatementDate.setOnClickListener {
            datePicker.show()
            datePicker.getButton(DatePickerDialog.BUTTON_NEGATIVE)
                .setTextColor(resources.getColor(R.color.bg_blue_multiply))
            datePicker.getButton(DatePickerDialog.BUTTON_POSITIVE)
                .setTextColor(resources.getColor(R.color.bg_blue_multiply))
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
            requireContext(),
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
