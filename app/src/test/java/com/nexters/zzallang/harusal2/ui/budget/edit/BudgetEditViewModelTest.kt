package com.nexters.zzallang.harusal2.ui.budget.edit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nexters.zzallang.harusal2.application.util.DateUtils
import com.nexters.zzallang.harusal2.application.util.NumberUtils
import com.nexters.zzallang.harusal2.data.entity.Budget
import com.nexters.zzallang.harusal2.rule.MainDispatcherRule
import com.nexters.zzallang.harusal2.usecase.BudgetUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class BudgetEditViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: BudgetEditViewModel
    private lateinit var budgetUseCase: BudgetUseCase

    @Before
    fun setUp() {
        budgetUseCase = mockk(relaxed = true)
        viewModel = BudgetEditViewModel(budgetUseCase)
    }

    // --- budgetChanged ---

    @Test
    fun `빈 문자열 입력 시 한글예산이 0원이다`() {
        viewModel.budgetChanged("")
        assertEquals("0원", viewModel.hangeulBudget.value)
    }

    @Test
    fun `빈 문자열 입력 시 평균예산이 일일 생활비 0원이다`() {
        viewModel.budgetChanged("")
        assertEquals("일일 생활비 0원", viewModel.averageBudget.value)
    }

    @Test
    fun `예산 입력 시 한글 변환이 된다`() {
        viewModel.budgetChanged("900000")
        assertEquals("90만원", viewModel.hangeulBudget.value)
    }

    @Test
    fun `예산 입력 시 일일 생활비가 이번달 일수로 나눠 계산된다`() {
        val duration = DateUtils.getLastDayOfThisMonth()
        val expected = "일일 생활비 ${NumberUtils.decimalFormat.format(300000L / duration)} 원"
        viewModel.budgetChanged("300000")
        assertEquals(expected, viewModel.averageBudget.value)
    }

    @Test
    fun `일일 생활비 150000원은 이번달 일수로 나눠 계산된다`() {
        val duration = DateUtils.getLastDayOfThisMonth()
        val expected = "일일 생활비 ${NumberUtils.decimalFormat.format(150000L / duration)} 원"
        viewModel.budgetChanged("150000")
        assertEquals(expected, viewModel.averageBudget.value)
    }

    @Test(expected = NumberFormatException::class)
    fun `숫자가 아닌 문자열 입력 시 예외가 발생한다`() {
        viewModel.budgetChanged("not_a_number")
    }

    // --- saveBudget ---

    @Test
    fun `currentBudget이 null이면 saveBudget은 false를 반환한다`() = runTest {
        // init()을 호출하지 않으면 currentBudget은 null
        viewModel.budget.value = "100000"
        val result = viewModel.saveBudget()
        assertFalse(result)
    }

    @Test
    fun `정상적으로 저장하면 true를 반환한다`() = runTest {
        val budget = Budget(
            id = 1L,
            budget = 100000,
            startDate = LocalDate.of(2026, 1, 1),
            endDate = LocalDate.of(2026, 1, 31)
        )
        coEvery { budgetUseCase.findRecentBudget() } returns budget

        viewModel.init()
        advanceUntilIdle()

        viewModel.budget.value = "200000"
        val result = viewModel.saveBudget()

        assertTrue(result)
        coVerify { budgetUseCase.updateBudget(any()) }
    }

    @Test
    fun `저장 시 예산 값이 업데이트된다`() = runTest {
        val budget = Budget(
            id = 1L,
            budget = 100000,
            startDate = LocalDate.of(2026, 1, 1),
            endDate = LocalDate.of(2026, 1, 31)
        )
        coEvery { budgetUseCase.findRecentBudget() } returns budget

        viewModel.init()
        advanceUntilIdle()

        viewModel.budget.value = "200000"
        viewModel.saveBudget()

        assertEquals(200000, budget.budget)
    }

    @Test
    fun `budget value가 빈 문자열이면 saveBudget은 false를 반환한다`() = runTest {
        val budget = Budget(
            id = 1L,
            budget = 100000,
            startDate = LocalDate.of(2026, 1, 1),
            endDate = LocalDate.of(2026, 1, 31)
        )
        coEvery { budgetUseCase.findRecentBudget() } returns budget

        viewModel.init()
        advanceUntilIdle()

        viewModel.budget.value = ""
        val result = viewModel.saveBudget()
        assertFalse(result)
    }

    // --- init ---

    @Test
    fun `init 호출 시 최근 예산을 로드한다`() = runTest {
        val budget = Budget(
            id = 1L,
            budget = 500000,
            startDate = LocalDate.of(2026, 1, 1),
            endDate = LocalDate.of(2026, 1, 31)
        )
        coEvery { budgetUseCase.findRecentBudget() } returns budget

        viewModel.init()
        advanceUntilIdle()

        assertEquals("500000", viewModel.budget.value)
    }
}
