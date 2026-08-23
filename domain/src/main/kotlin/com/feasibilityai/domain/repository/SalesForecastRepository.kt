package com.feasibilityai.domain.repository

import com.feasibilityai.domain.model.SalesForecastEntry
import kotlinx.coroutines.flow.Flow
import java.time.YearMonth

interface SalesForecastRepository {

    /** All forecast entries for a project, across all products and periods — the Financial Engine's primary read. */
    fun observeAllForProject(projectId: String): Flow<List<SalesForecastEntry>>

    /** Entries for one product only, for the Sales Forecast screen's per-product grid view. */
    fun observeForProduct(projectId: String, productId: String): Flow<List<SalesForecastEntry>>

    suspend fun upsertEntry(entry: SalesForecastEntry): SalesForecastEntry

    suspend fun deleteEntry(entryId: String)

    /** Bulk upsert for a full period grid save (e.g. applying a growth rate across 60 months at once) — avoids 60 sequential single-row writes. */
    suspend fun upsertBatch(entries: List<SalesForecastEntry>)
}
