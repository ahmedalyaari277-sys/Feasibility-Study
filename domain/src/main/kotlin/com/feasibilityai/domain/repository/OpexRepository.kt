package com.feasibilityai.domain.repository

import com.feasibilityai.domain.model.OpexItem
import kotlinx.coroutines.flow.Flow

interface OpexRepository {

    fun observeAllForProject(projectId: String): Flow<List<OpexItem>>

    fun observeItem(itemId: String): Flow<OpexItem?>

    suspend fun getItemOnce(itemId: String): OpexItem?

    suspend fun upsertItem(item: OpexItem): OpexItem

    suspend fun deleteItem(itemId: String)
}
