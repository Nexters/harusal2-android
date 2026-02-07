package com.nexters.zzallang.harusal2.ui.budget.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nexters.zzallang.harusal2.rule.MainDispatcherRule
import com.nexters.zzallang.harusal2.usecase.BudgetUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class StartDayClickRegisterViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: StartDayClickRegisterViewModel
    private lateinit var budgetUseCase: BudgetUseCase

    @Before
    fun setUp() {
        budgetUseCase = mockk(relaxed = true)
        viewModel = StartDayClickRegisterViewModel(budgetUseCase)
    }

    // --- init ---

    @Test
    fun `초기화시_pickedDate가_오늘의_dayOfMonth로_설정된다`() {
        val today = LocalDate.now()
        assertEquals(today.dayOfMonth, viewModel.pickedDate.value)
    }

    // --- pickedDayChanged ---

    @Test
    fun `pickedDayChanged 호출 시 pickedDate가 갱신된다`() {
        viewModel.pickedDayChanged(15)
        assertEquals(15, viewModel.pickedDate.value)
    }

    @Test
    fun `pickedDayChanged 호출 시 description이 갱신된다`() {
        viewModel.pickedDayChanged(1)
        assertNotNull(viewModel.description.value)
        assertTrue(viewModel.description.value!!.startsWith("생활비 사용기간은 "))
    }

    @Test
    fun `pickedDayChanged 1일 선택 시 description에 시작일이 포함된다`() {
        viewModel.pickedDayChanged(1)
        val desc = viewModel.description.value!!
        assertTrue(desc.contains(".1 - "))
    }

    @Test
    fun `pickedDayChanged 시 시작일과 종료일 기간이 description에 표시된다`() {
        val today = LocalDate.now()
        // dayOfMonth 이하 날짜를 선택하면 이번 달부터 시작
        val pickDay = minOf(today.dayOfMonth, 15)
        viewModel.pickedDayChanged(pickDay)

        val desc = viewModel.description.value!!
        assertTrue(desc.contains("생활비 사용기간은 "))
        assertTrue(desc.contains(" - "))
    }

    // --- saveBudgetDay ---

    @Test
    fun `saveBudgetDay 호출 시 budgetUseCase_insertBudget이 호출된다`() = runTest {
        viewModel.saveBudgetDay(300000)
        advanceUntilIdle()

        coVerify { budgetUseCase.insertBudget(300000, startDate = any(), endDate = any()) }
    }
}
