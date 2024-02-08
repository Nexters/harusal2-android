package com.nexters.zzallang.harusal2.data.repository

import com.nexters.zzallang.harusal2.application.AppDatabase
import com.nexters.zzallang.harusal2.data.entity.Budget

class BudgetRepository {
    suspend fun insertBudget(budget: Budget) {
        AppDatabase.instance.budgetDao().insertBudget(budget)
    }

    suspend fun findById(id: Long): Budget {
        return AppDatabase.instance.budgetDao().findById(id)
    }

    suspend fun findRecentBudget(): Budget {
        return AppDatabase.instance.budgetDao().findRecentBudget()
    }

    suspend fun update(budget: Budget) {
        AppDatabase.instance.budgetDao().update(budget)
    }

    suspend fun findAll(): List<Budget> {
        return AppDatabase.instance.budgetDao().findAll()
    }

    suspend fun findAllDesc(): List<Budget> {
        return AppDatabase.instance.budgetDao().findAllDesc()
    }

    suspend fun deleteAllBudget() {
        AppDatabase.instance.budgetDao().deleteAllBudget()
    }
}
