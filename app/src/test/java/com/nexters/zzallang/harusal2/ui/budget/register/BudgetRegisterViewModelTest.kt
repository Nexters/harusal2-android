package com.nexters.zzallang.harusal2.ui.budget.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nexters.zzallang.harusal2.application.util.DateUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BudgetRegisterViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: BudgetRegisterViewModel

    @Before
    fun setUp() {
        viewModel = BudgetRegisterViewModel()
    }

    @Test
    fun `빈 문자열 입력 시 한글예산이 0원이다`() {
        viewModel.budgetChanged("")
        assertEquals("0원", viewModel.hangeulBudget.value)
    }

    @Test
    fun `빈 문자열 입력 시 평균예산이 0원이다`() {
        viewModel.budgetChanged("")
        assertEquals("0원", viewModel.averageBudget.value)
    }

    @Test
    fun `예산 입력 시 한글 변환이 된다`() {
        viewModel.budgetChanged("300000")
        assertEquals("30만원", viewModel.hangeulBudget.value)
    }

    @Test
    fun `예산 입력 시 평균예산이 이번달 일수로 나눠 계산된다`() {
        val duration = DateUtils.getLastDayOfThisMonth()
        val expected = (300000L / duration).toString() + "원"
        viewModel.budgetChanged("300000")
        assertEquals(expected, viewModel.averageBudget.value)
    }

    @Test
    fun `0 입력 시 한글예산이 0원이다`() {
        viewModel.budgetChanged("0")
        assertEquals("0원", viewModel.hangeulBudget.value)
    }

    @Test
    fun `0 입력 시 평균예산이 0원이다`() {
        viewModel.budgetChanged("0")
        assertEquals("0원", viewModel.averageBudget.value)
    }

    @Test
    fun `큰 금액 입력 시 한글 변환이 된다`() {
        viewModel.budgetChanged("150000000")
        assertEquals("1억5000만원", viewModel.hangeulBudget.value)
    }

    @Test(expected = NumberFormatException::class)
    fun `숫자가 아닌 문자열 입력 시 NumberFormatException이 발생한다`() {
        viewModel.budgetChanged("abc")
    }

    @Test
    fun `textClear 호출 시 한글예산이 0원이다`() {
        viewModel.budgetChanged("300000")
        viewModel.textClear()
        assertEquals("0원", viewModel.hangeulBudget.value)
    }

    @Test
    fun `textClear 호출 시 평균예산이 0원이다`() {
        viewModel.budgetChanged("300000")
        viewModel.textClear()
        assertEquals("0원", viewModel.averageBudget.value)
    }
}
