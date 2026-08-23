package com.feasibilityai.domain.model

import java.math.BigDecimal

data class Product(
    val id: String,
    val projectId: String,
    val name: String,
    val productCode: String,
    val unit: String, // free text (e.g. "carton", "bottle") — deliberately not UnitOfMeasure, since a finished product's sales unit is a business decision, not a raw-material measurement unit
    val weight: BigDecimal? = null,
    val sellingPrice: MoneyValue,
    val description: String? = null,
    val imagePath: String? = null
) {
    init {
        require(name.isNotBlank()) { "Product name must not be blank." }
        require(productCode.isNotBlank()) { "Product code must not be blank." }
        require(unit.isNotBlank()) { "Product unit must not be blank." }
        weight?.let { require(it >= BigDecimal.ZERO) { "Product weight must not be negative." } }
    }
}
