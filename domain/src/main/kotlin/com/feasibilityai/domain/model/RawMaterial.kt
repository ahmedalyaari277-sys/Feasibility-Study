package com.feasibilityai.domain.model

import java.math.BigDecimal

data class RawMaterial(
    val id: String,
    val projectId: String,
    val name: String,
    val unit: UnitOfMeasure,
    val customUnitLabel: String? = null, // required when unit == CUSTOM
    val quantity: BigDecimal,
    val unitCost: MoneyValue,
    val wastePercent: BigDecimal,
    val supplierId: String? = null
) {
    init {
        require(name.isNotBlank()) { "RawMaterial name must not be blank." }
        require(quantity >= BigDecimal.ZERO) { "RawMaterial quantity must not be negative." }
        require(wastePercent in BigDecimal.ZERO..BigDecimal(100)) {
            "wastePercent must be between 0 and 100 (SRS §9 Validation Rules), got $wastePercent."
        }
        if (unit == UnitOfMeasure.CUSTOM) {
            require(!customUnitLabel.isNullOrBlank()) { "customUnitLabel is required when unit is CUSTOM." }
        }
    }

    /** Effective unit cost including waste, per Costing Rules §11. */
    val effectiveUnitCost: MoneyValue
        get() {
            val wasteFactor = BigDecimal.ONE + (wastePercent / BigDecimal(100))
            return unitCost * wasteFactor
        }
}

enum class UnitOfMeasure {
    TON, KG, GRAM, LITER, ML, PIECE, CUSTOM
}
