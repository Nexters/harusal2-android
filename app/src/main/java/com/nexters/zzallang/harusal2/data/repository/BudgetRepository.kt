package com.nexters.zzallang.harusal2.data.repository

import com.nexters.zzallang.harusal2.application.AppDatabase
import com.nexters.zzallang.harusal2.base.BaseRepository
import com.nexters.zzallang.harusal2.data.entity.Budget
import kotlinx.coroutines.withContext

class BudgetRepository : BaseRepository() {
    suspend fun insertBudget(budget: Budget) =
        withContext(coroutineContext) {
            AppDatabase.instance.periodDao().insertBudget(budget)
        }

    suspend fun findById(id: Long) =
        withContext(coroutineContext) {
            AppDatabase.instance.periodDao().findById(id)
        }

    suspend fun findRecentBudget() =
        withContext(coroutineContext) {
            AppDatabase.instance.periodDao().findRecentBudget()
        }

    suspend fun update(budget: Budget) =
        withContext(coroutineContext) {
            AppDatabase.instance.periodDao().update(budget)
        }

    suspend fun findAll() =
        withContext(coroutineContext) {
            AppDatabase.instance.periodDao().findAll()
        }

    suspend fun findAllDesc() = withContext(coroutineContext) {
        AppDatabase.instance.periodDao().findAllDesc()
    }
}