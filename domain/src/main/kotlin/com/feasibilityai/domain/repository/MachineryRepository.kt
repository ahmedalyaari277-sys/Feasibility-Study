package com.feasibilityai.domain.repository

import com.feasibilityai.domain.model.Machinery
import kotlinx.coroutines.flow.Flow

interface MachineryRepository {

    fun observeAllForProject(projectId: String): Flow<List<Machinery>>

    fun observeItem(itemId: String): Flow<Machinery?>

    suspend fun getItemOnce(itemId: String): Machinery?

    suspend fun upsertItem(item: Machinery): Machinery

    suspend fun deleteItem(itemId: String)

    suspend fun importFromLibrary(projectId: String, libraryEntryId: String): Machinery
}
