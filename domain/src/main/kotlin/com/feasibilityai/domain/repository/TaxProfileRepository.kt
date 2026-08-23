package com.feasibilityai.domain.repository

import com.feasibilityai.domain.model.TaxProfile
import kotlinx.coroutines.flow.Flow

/**
 * A project has at most one TaxProfile (see TaxProfile model doc) — this contract
 * reads/writes a single nullable record, not a collection, unlike every other
 * repository in this module.
 */
interface TaxProfileRepository {

    fun observeForProject(projectId: String): Flow<TaxProfile?>

    suspend fun getForProjectOnce(projectId: String): TaxProfile?

    suspend fun upsert(taxProfile: TaxProfile): TaxProfile
}
