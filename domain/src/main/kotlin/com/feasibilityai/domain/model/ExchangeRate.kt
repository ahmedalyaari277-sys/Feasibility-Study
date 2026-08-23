package com.feasibilityai.domain.model

import java.math.BigDecimal
import java.time.LocalDate

/**
 * One exchange rate revision for a currency against USD (the fixed base currency —
 * Currency Rules §14). Multiple ExchangeRate rows can exist for the same currencyCode
 * with different effectiveDate values; ExchangeRateResolver (engine:currency-engine)
 * picks whichever revision's effectiveDate is the latest one on-or-before a given
 * value's entry/edit date — never "today's" rate for historical values.
 */
data class ExchangeRate(
    val id: String,
    val currencyCode: String,
    val rateToUsd: BigDecimal, // 1 unit of currencyCode = rateToUsd USD
    val effectiveDate: LocalDate
) {
    init {
        require(currencyCode.isNotBlank()) { "currencyCode must not be blank." }
        require(currencyCode == currencyCode.uppercase()) { "currencyCode must be uppercase ISO 4217 style." }
        require(rateToUsd > BigDecimal.ZERO) { "rateToUsd must be positive, got $rateToUsd." }
    }
}
