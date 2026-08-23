package com.feasibilityai.domain.repository

import com.feasibilityai.domain.model.CapexItem
import kotlinx.coroutines.flow.Flow

/**
 * Contract for CapexItem persistence, scoped to a single project. This shape
 * (observeAllForProject / observeOne / getOnce / upsert / delete) repeats identically
 * for every other entity repository in this module — CapexItem is the reference
 * implementation of the pattern described in screen spec §20.1/20.2.
 */
interface CapexRepository {

    fun observeAllForProject(projectId: String): Flow<List<CapexItem>>

    fun observeItem(itemId: String): Flow<CapexItem?>

    suspend fun getItemOnce(itemId: String): CapexItem?

    suspend fun upsertItem(item: CapexItem): CapexItem

    /** Delete requires confirmation at the UI layer if the item has dependents (§9) — by the time this is called, confirmation has already happened. */
    suspend fun deleteItem(itemId: String)
}
