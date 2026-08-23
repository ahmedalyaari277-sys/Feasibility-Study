package com.feasibilityai.domain.repository

import com.feasibilityai.domain.model.Utility
import kotlinx.coroutines.flow.Flow

interface UtilityRepository {

    fun observeAllForProject(projectId: String): Flow<List<Utility>>

    fun observeItem(itemId: String): Flow<Utility?>

    suspend fun getItemOnce(itemId: String): Utility?

    suspend fun upsertItem(item: Utility): Utility

    suspend fun deleteItem(itemId: String)
}
