package com.feasibilityai.domain.repository

import com.feasibilityai.domain.model.ExchangeRate
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

/**
 * Contract for ExchangeRate persistence and resolution. resolveRateFor is the single
 * source of truth for "which rate applies to this date" (Currency Rules §14) — no
 * caller should hand-roll its own date comparison against a raw list of rates.
 */
interface ExchangeRateRepository {

    fun observeAllForCurrency(currencyCode: String): Flow<List<ExchangeRate>>

    /** All currencies with at least one rate defined — for the Currency & Exchange Rates screen's currency list. */
    fun observeAllCurrencies(): Flow<List<String>>

    suspend fun upsertRate(rate: ExchangeRate): ExchangeRate

    suspend fun deleteRate(rateId: String)

    /**
     * Resolves the correct ExchangeRate for a given currency and reference date: the
     * revision whose effectiveDate is the latest one ≤ referenceDate (§14). Returns
     * null if no rate exists for that currency on or before referenceDate at all —
     * callers must handle this (e.g. block save until a rate exists), not assume 1:1.
     */
    suspend fun resolveRateFor(currencyCode: String, referenceDate: LocalDate): ExchangeRate?

    /**
     * True if inserting a rate at insertDate would predate an already-used rate for
     * the same currency (i.e. a Report snapshot or live value already resolved against
     * a later-effective-date rate). Backs the "historical backdating warning" (§9/§14) —
     * the UI shows a confirmation dialog when this returns true, it does not block outright.
     */
    suspend fun wouldBackdateExistingUsage(currencyCode: String, insertDate: LocalDate): Boolean
}
