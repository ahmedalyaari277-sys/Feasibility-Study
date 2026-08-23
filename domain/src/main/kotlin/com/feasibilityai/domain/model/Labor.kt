package com.feasibilityai.domain.model

data class Labor(
    val id: String,
    val projectId: String,
    val position: String,
    val quantity: Int,
    val salary: MoneyValue,
    val benefits: MoneyValue,
    val insurance: MoneyValue,
    val overtime: MoneyValue? = null,
    val shiftType: ShiftType
) {
    init {
        require(position.isNotBlank()) { "Labor position must not be blank." }
        require(quantity > 0) { "Labor quantity must be positive, got $quantity." }
    }

    /** Total monthly cost for this line (salary + benefits + insurance + overtime) × headcount. Overtime, when absent, contributes zero. */
    fun totalMonthlyCost(): MoneyValue {
        val perHead = salary + benefits + insurance + (overtime ?: MoneyValue.zero(salary.currencyCode))
        return perHead * java.math.BigDecimal(quantity)
    }
}

enum class ShiftType {
    SINGLE_SHIFT, DOUBLE_SHIFT, TRIPLE_SHIFT, CUSTOM
}
