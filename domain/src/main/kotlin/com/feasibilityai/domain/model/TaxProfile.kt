package com.feasibilityai.domain.model

import java.math.BigDecimal

/**
 * One TaxProfile per project (not per-scenario — tax policy doesn't vary by
 * Conservative/Expected/Optimistic in v1; only revenue/cost assumptions do).
 *
 * depreciationMethod is restricted to STRAIGHT_LINE in v1 (Financial Rules §10.9,
 * confirmed decision) — the enum has only one member deliberately, so adding
 * DECLINING_BALANCE later is additive, not a breaking change to this model's shape.
 */
data class TaxProfile(
    val id: String,
    val projectId: String,
    val corporateTaxRatePercent: BigDecimal,
    val vatRatePercent: BigDecimal? = null,
    val depreciationMethod: DepreciationMethod = DepreciationMethod.STRAIGHT_LINE
) {
    init {
        require(corporateTaxRatePercent in BigDecimal.ZERO..BigDecimal(100)) {
            "corporateTaxRatePercent must be between 0 and 100, got $corporateTaxRatePercent."
        }
        vatRatePercent?.let {
            require(it in BigDecimal.ZERO..BigDecimal(100)) { "vatRatePercent must be between 0 and 100, got $it." }
        }
    }
}

enum class DepreciationMethod {
    STRAIGHT_LINE
    // Accelerated/declining-balance methods deferred to a later version (Financial Rules §10.9).
}
