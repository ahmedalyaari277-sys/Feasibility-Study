package com.feasibilityai.domain.model

import java.math.BigDecimal

data class Machinery(
    val id: String,
    val projectId: String,
    val capexItemId: String, // links back to the CapexItem this machine was recorded under
    val name: String,
    val supplierId: String? = null,
    val country: String? = null,
    val price: MoneyValue,
    val powerConsumptionKw: BigDecimal? = null,
    val capacity: String? = null, // free text: capacity is unit-dependent per machine type
    val maintenanceCostPerYear: MoneyValue? = null,
    val expectedLifeYears: Int? = null,
    val attachmentIds: List<String> = emptyList()
) {
    init {
        require(name.isNotBlank()) { "Machinery name must not be blank." }
        expectedLifeYears?.let {
            require(it > 0) { "expectedLifeYears must be positive if provided, got $it." }
        }
    }
}
