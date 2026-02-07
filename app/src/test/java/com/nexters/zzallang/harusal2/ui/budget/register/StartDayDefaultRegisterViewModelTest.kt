package com.nexters.zzallang.harusal2.ui.budget.register

import com.nexters.zzallang.harusal2.rule.MainDispatcherRule
import com.nexters.zzallang.harusal2.usecase.BudgetUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class StartDayDefaultRegisterViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: StartDayDefaultRegisterViewModel
    private lateinit var budgetUseCase: BudgetUseCase

    @Before
    fun setUp() {
        budgetUseCase = mockk(relaxed = true)
        viewModel = StartDayDefaultRegisterViewModel(budgetUseCase)
    }

    @Test
    fun `getDay는 오늘_날짜의_일을_반환한다`() {
        assertEquals(LocalDate.now().dayOfMonth, viewModel.getDay())
    }

    @Test
    fun `getDescription은_오늘_일을_포함한다`() {
        val day = LocalDate.now().dayOfMonth
        assertTrue(viewModel.getDescription().contains("${day}일을"))
    }

    @Test
    fun `getDescription_형식이_올바르다`() {
        val desc = viewModel.getDescription()
        assertTrue(desc.startsWith("매달 "))
        assertTrue(desc.contains("시작일자로 할까요?"))
    }

    @Test
    fun `getDescription은_줄바꿈을_포함한다`() {
        assertTrue(viewModel.getDescription().contains("\n"))
    }

    @Test
    fun `saveBudgetDay 호출 시 budgetUseCase_insertBudget이_호출된다`() = runTest {
        viewModel.saveBudgetDay(500000)
        advanceUntilIdle()

        coVerify {
            budgetUseCase.insertBudget(
                500000,
                LocalDate.now()
            )
        }
    }
}
