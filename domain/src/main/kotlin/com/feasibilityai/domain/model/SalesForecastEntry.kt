package com.feasibilityai.domain.model

import java.math.BigDecimal
import java.time.YearMonth

data class SalesForecastEntry(
    val id: String,
    val projectId: String,
    val productId: String,
    val period: YearMonth, // always stored at month granularity; quarterly/yearly are UI-level aggregations of monthly entries, not separate storage
    val forecastedUnits: BigDecimal,
    val growthRatePercent: BigDecimal? = null,
    val seasonalityFactor: BigDecimal? = null // multiplier applied on top of the base forecast, e.g. 1.2 for a peak month
) {
    init {
        require(forecastedUnits >= BigDecimal.ZERO) { "forecastedUnits must not be negative." }
        seasonalityFactor?.let {
            require(it >= BigDecimal.ZERO) { "seasonalityFactor must not be negative." }
        }
    }

    /** Revenue for this period = forecasted units × the product's selling price. Seasonality, if present, is already baked into forecastedUnits by the time this is called — the Financial Engine reads forecastedUnits as final, not raw. */
    fun revenue(product: Product): MoneyValue = product.sellingPrice * forecastedUnits
}
