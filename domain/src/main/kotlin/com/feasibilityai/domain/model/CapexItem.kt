package com.feasibilityai.domain.model

/**
 * A single CAPEX line item. Dynamic by design — `category` is free text
 * ("Land", "Machinery", "Solar Plant", or any user-defined custom asset),
 * not a fixed enum, per the CAPEX Module's "no fixed fields" requirement.
 */
data class CapexItem(
    val id: String,
    val projectId: String,
    val category: String,
    val name: String,
    val value: MoneyValue,
    val supplierId: String? = null,
    val description: String? = null,
    val attachmentIds: List<String> = emptyList()
) {
    init {
        require(category.isNotBlank()) { "CapexItem category must not be blank." }
        require(name.isNotBlank()) { "CapexItem name must not be blank." }
    }
}
