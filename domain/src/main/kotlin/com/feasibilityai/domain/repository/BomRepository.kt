package com.feasibilityai.domain.repository

import com.feasibilityai.domain.model.BomEntry
import kotlinx.coroutines.flow.Flow

/**
 * BOM is keyed by product, not a flat item list — a product has at most one BomEntry
 * in v1 (no BOM versioning yet, per Feature Matrix "Recipe versioning" deferred to v2).
 */
interface BomRepository {

    fun observeForProduct(productId: String): Flow<BomEntry?>

    suspend fun getForProductOnce(productId: String): BomEntry?

    suspend fun upsert(entry: BomEntry): BomEntry

    suspend fun deleteForProduct(productId: String)

    /**
     * Copies a BomTemplateLibraryEntity's material/packaging lines onto a new BomEntry
     * for the given product (§12 — "Apply Template" button, BOM Builder screen §20.14).
     */
    suspend fun applyTemplate(projectId: String, productId: String, templateId: String): BomEntry
}
