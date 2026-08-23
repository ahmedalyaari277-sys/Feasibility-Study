package com.feasibilityai.domain.model

/**
 * One packaging level within a project's packaging hierarchy (§13). Exactly three
 * fixed levels are supported in v1 — custom additional levels are out of scope.
 */
data class PackagingLevel(
    val id: String,
    val projectId: String,
    val level: PackagingLevelType,
    val name: String,
    val unitsPerParent: Int? = null, // e.g. Carton.unitsPerParent = 24 means "1 Carton = 24 Packages"
    val cost: MoneyValue
) {
    init {
        require(name.isNotBlank()) { "PackagingLevel name must not be blank." }
        when (level) {
            PackagingLevelType.PRIMARY ->
                require(unitsPerParent == null) { "PRIMARY packaging has no parent — unitsPerParent must be null." }
            PackagingLevelType.SECONDARY, PackagingLevelType.TERTIARY ->
                require(unitsPerParent != null && unitsPerParent >= 1) {
                    "unitsPerParent must be a positive integer for SECONDARY/TERTIARY levels (§9 Validation Rules)."
                }
        }
    }
}

enum class PackagingLevelType {
    PRIMARY,   // Package
    SECONDARY, // Carton
    TERTIARY   // Pallet
}
