package com.feasibilityai.domain.model

import java.math.BigDecimal

/**
 * A loan/financing line for a project. Required for DSCR and Equity IRR to be
 * computable (Financial Rules §10.10) — a project with no Loan records is legitimate
 * (all-equity), in which case DSCR displays "N/A — no financing defined" rather than
 * a misleading zero (see FinancialEngineOutput).
 */
data class Loan(
    val id: String,
    val projectId: String,
    val lenderName: String? = null,
    val principal: MoneyValue,
    val annualRatePercent: BigDecimal,
    val tenorMonths: Int,
    val graceMonths: Int
) {
    init {
        require(annualRatePercent >= BigDecimal.ZERO) { "annualRatePercent must not be negative." }
        require(tenorMonths > 0) { "tenorMonths must be positive, got $tenorMonths." }
        require(graceMonths >= 0) { "graceMonths must not be negative, got $graceMonths." }
        require(graceMonths < tenorMonths) {
            "graceMonths ($graceMonths) must be less than tenorMonths ($tenorMonths) — SRS §9 Validation Rules."
        }
    }
}
