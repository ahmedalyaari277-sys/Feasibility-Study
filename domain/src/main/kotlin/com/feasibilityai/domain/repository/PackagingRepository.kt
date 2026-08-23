package com.feasibilityai.domain.repository

import com.feasibilityai.domain.model.PackagingLevel
import kotlinx.coroutines.flow.Flow

interface PackagingRepository {

    fun observeAllForProject(projectId: String): Flow<List<PackagingLevel>>

    fun observeItem(itemId: String): Flow<PackagingLevel?>

    suspend fun getItemOnce(itemId: String): PackagingLevel?

    suspend fun upsertItem(item: PackagingLevel): PackagingLevel

    suspend fun deleteItem(itemId: String)

    suspend fun importFromLibrary(projectId: String, libraryEntryId: String): PackagingLevel

    /**
     * Returns all packaging levels a specific product's BOM references (Primary +
     * Secondary + Tertiary, where present), fetched together so BomEngine's rollup
     * calculation (§12/§13) doesn't need three round trips.
     */
    suspend fun getHierarchyForProduct(productId: String): List<PackagingLevel>
}
