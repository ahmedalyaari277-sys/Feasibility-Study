package com.feasibilityai.domain.model

/**
 * A single OPEX line item. Dynamic by design, matching CapexItem's structure —
 * category is free text, not a fixed enum.
 */
data class OpexItem(
    val id: String,
    val projectId: String,
    val category: String,
    val name: String,
    val value: MoneyValue,
    val description: String? = null
) {
    init {
        require(category.isNotBlank()) { "OpexItem category must not be blank." }
        require(name.isNotBlank()) { "OpexItem name must not be blank." }
    }
}
