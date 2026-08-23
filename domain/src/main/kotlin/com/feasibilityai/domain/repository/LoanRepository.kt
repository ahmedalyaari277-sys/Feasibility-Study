package com.feasibilityai.domain.repository

import com.feasibilityai.domain.model.Loan
import kotlinx.coroutines.flow.Flow

interface LoanRepository {

    fun observeAllForProject(projectId: String): Flow<List<Loan>>

    fun observeItem(itemId: String): Flow<Loan?>

    suspend fun getItemOnce(itemId: String): Loan?

    suspend fun upsertItem(item: Loan): Loan

    suspend fun deleteItem(itemId: String)
}
