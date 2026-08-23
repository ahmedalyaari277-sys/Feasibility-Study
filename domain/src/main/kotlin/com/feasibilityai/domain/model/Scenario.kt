package com.feasibilityai.domain.model

import java.math.BigDecimal

/**
 * A Scenario is a named multiplier set applied on top of a project's live data when
 * the Financial Engine runs (FinancialEngineInput.scenario). Multipliers, not absolute
 * overrides — this way a scenario stays coherent even as CAPEX/OPEX/Sales data changes
 * underneath it; "Optimistic" always means "+15% revenue" relative to current data,
 * not a frozen number from when the scenario was created.
 *
 * Every project gets 3 built-in scenarios at creation (type != CUSTOM); the user may
 * add unlimited additional CUSTOM ones.
 */
data class Scenario(
    val id: String,
    val projectId: String,
    val name: String,
    val type: ScenarioType,
    val revenueMultiplier: BigDecimal = BigDecimal.ONE,
    val rawMaterialCostMultiplier: BigDecimal = BigDecimal.ONE,
    val opexMultiplier: BigDecimal = BigDecimal.ONE,
    val discountRateAdjustmentPercent: BigDecimal = BigDecimal.ZERO // added to (not multiplied against) the base discount rate
) {
    init {
        require(name.isNotBlank()) { "Scenario name must not be blank." }
        require(revenueMultiplier >= BigDecimal.ZERO) { "revenueMultiplier must not be negative." }
        require(rawMaterialCostMultiplier >= BigDecimal.ZERO) { "rawMaterialCostMultiplier must not be negative." }
        require(opexMultiplier >= BigDecimal.ZERO) { "opexMultiplier must not be negative." }
    }
}

enum class ScenarioType {
    CONSERVATIVE, EXPECTED, OPTIMISTIC, CUSTOM
}
