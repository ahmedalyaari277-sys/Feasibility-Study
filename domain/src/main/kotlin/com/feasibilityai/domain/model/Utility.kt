package com.feasibilityai.domain.model

data class Utility(
    val id: String,
    val projectId: String,
    val type: UtilityType,
    val customTypeLabel: String? = null, // required when type == CUSTOM
    val cost: MoneyValue,
    val consumptionUnit: String? = null // e.g. "kWh/month", free text since units vary widely
) {
    init {
        if (type == UtilityType.CUSTOM) {
            require(!customTypeLabel.isNullOrBlank()) { "customTypeLabel is required when type is CUSTOM." }
        }
    }
}

enum class UtilityType {
    ELECTRICITY, WATER, DIESEL, GAS, INTERNET, STEAM, COMPRESSED_AIR, CUSTOM
}
