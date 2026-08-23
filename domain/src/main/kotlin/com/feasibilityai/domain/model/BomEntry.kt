package com.feasibilityai.domain.model

import java.math.BigDecimal

/**
 * A product's Bill of Materials: which raw materials and packaging it consumes,
 * at what quantity per unit of product, with yield/waste applied. This is the
 * input model consumed by BomEngine (engine:bom-engine) — BomEntry itself holds
 * no computed cost; that's the engine's responsibility (SRS §12).
 */
data class BomEntry(
    val id: String,
    val projectId: String,
    val productId: String,
    val materialLines: List<BomMaterialLine>,
    val packagingLines: List<BomPackagingLine>,
    val yieldPercent: BigDecimal
) {
    init {
        require(yieldPercent in BigDecimal.ZERO..BigDecimal(100)) {
            "yieldPercent must be between 0 and 100, got $yieldPercent."
        }
    }
}

data class BomMaterialLine(
    val rawMaterialId: String,
    val quantityPerUnit: BigDecimal,
    val wastePercent: BigDecimal
) {
    init {
        require(quantityPerUnit >= BigDecimal.ZERO) { "quantityPerUnit must not be negative." }
        require(wastePercent in BigDecimal.ZERO..BigDecimal(100)) { "wastePercent must be between 0 and 100." }
    }
}

data class BomPackagingLine(
    val packagingLevelId: String,
    val quantityPerUnit: BigDecimal
) {
    init {
        require(quantityPerUnit >= BigDecimal.ZERO) { "quantityPerUnit must not be negative." }
    }
}
