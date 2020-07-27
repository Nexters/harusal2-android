package com.nexters.zzallang.harusal2.data.repository

import com.nexters.zzallang.harusal2.application.AppDatabase
import com.nexters.zzallang.harusal2.base.BaseRepository
import com.nexters.zzallang.harusal2.data.entity.Budget
import kotlinx.coroutines.withContext

class BudgetRepository: BaseRepository() {
    suspend fun insertBudget(budget: Budget) =
        withContext(coroutineContext){
            AppDatabase.instance.budgetDao().insertBudget(budget)
        }

    suspend fun getAllBudgets(): List<Budget> =
        withContext(coroutineContext){
            AppDatabase.instance.budgetDao().getAllBudgets()
        }

    suspend fun getBudgetWithId(budgetId: Long): Budget =
        withContext(coroutineContext){
            AppDatabase.instance.budgetDao().getBudgetWithId(budgetId)
        }


    suspend fun updateBudget(budget: Budget) =
        withContext(coroutineContext){
            AppDatabase.instance.budgetDao().updateBudget(budget)
        }

    suspend fun updateBudgetAmountWithId(budgetId: Long, amount: Long) =
        withContext(coroutineContext){
            AppDatabase.instance.budgetDao().updateBudgetAmountWithId(budgetId, amount)
        }

    suspend fun updateBudgetDateWithId(budgetId: Long, startDate: Int) =
        withContext(coroutineContext){
            AppDatabase.instance.budgetDao().updateBudgetDateWithId(budgetId, startDate)
        }

    suspend fun deleteBudgetWithId(budgetId: Long) =
        withContext(coroutineContext){
            AppDatabase.instance.budgetDao().deleteBudgetWithId(budgetId)
        }
}