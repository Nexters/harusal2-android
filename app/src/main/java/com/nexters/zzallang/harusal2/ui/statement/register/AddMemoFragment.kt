package com.nexters.zzallang.harusal2.ui.statement.register

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.application.util.Constants
import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.application.util.IntentUtils.getMainActivityIntent
import com.nexters.zzallang.harusal2.base.BaseFragment
import com.nexters.zzallang.harusal2.databinding.FragmentAddMemoBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.util.*

class AddMemoFragment : BaseFragment<FragmentAddMemoBinding>() {
    override fun layoutRes(): Int = R.layout.fragment_add_memo
    override val viewModel: AddMemoViewModel by viewModel()
    private val addStatementViewModel: AddStatementViewModel by activityViewModels()

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

        if("MAIN" == addStatementViewModel.getBeforeActivity()){
            viewModel.setDate(viewModel.getDateForNow())
        }

        viewModel.stateDate.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it == ""){
                binding.btnStatementDone.setBackgroundColor(resources.getColor(R.color.line))
                binding.btnStatementDone.isEnabled = false
            } else{
                binding.btnStatementDone.setBackgroundColor(resources.getColor(R.color.point_color))
                binding.btnStatementDone.isEnabled = true
            }
        })

        binding.btnStatementPre.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.btnStatementDone.setOnClickListener {
            GlobalScope.launch {
                viewModel.createStatement()
            }

            activity?.supportFragmentManager
                    ?.beginTransaction()
                    ?.remove(this)
                    ?.commit()

            activity?.startActivity(getMainActivityIntent(context))
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

        GlobalScope.launch {
            datePickerDialog.datePicker.minDate = viewModel.getMinDate()
            datePickerDialog.datePicker.maxDate = viewModel.getMaxDate()
        }

        return datePickerDialog
    }
}