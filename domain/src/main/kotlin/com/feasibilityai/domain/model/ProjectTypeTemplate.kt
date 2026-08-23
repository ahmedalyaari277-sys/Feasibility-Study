package com.feasibilityai.domain.model

/**
 * A Project Type Template (SRS §3a). Declares which modules a project of this type
 * shows, any module-level overrides, and any extra industry-specific fields.
 *
 * The financial engine, BOM engine, and database schema are IDENTICAL regardless of
 * typeId — a template only ever changes what the UI renders and what extra
 * CustomFieldDefinition entries exist. This is what makes adding new verticals cheap.
 *
 * v1 ships FOOD_FACTORY and NON_FOOD_MANUFACTURING only (confirmed scope).
 */
data class ProjectTypeTemplate(
    val typeId: String,
    val displayNameEn: String,
    val displayNameAr: String,
    val visibleModules: Set<ProjectModule>,
    val moduleOverrides: Map<ProjectModule, ModuleOverride> = emptyMap(),
    val customFields: List<CustomFieldDefinition> = emptyList(),
    val defaultKnowledgeBaseCategory: String
) {
    companion object {
        const val FOOD_FACTORY = "food_factory"
        const val NON_FOOD_MANUFACTURING = "non_food_manufacturing"
    }
}

/**
 * Every module a template can choose to show/hide. This enum is the single source of
 * truth the UI layer (feature/* modules) reads to decide what to render for a project —
 * it must be kept in sync with the Screen Tree (SRS §6) module list.
 */
enum class ProjectModule {
    CAPEX,
    OPEX,
    RAW_MATERIALS,
    PACKAGING,
    BOM,
    MACHINERY,
    LABOR,
    UTILITIES,
    PRODUCTS,
    SALES_FORECAST,
    FINANCING,
    TAX_PROFILE
}

/**
 * Describes how a template reshapes a module's default structure — e.g. a future
 * Real Estate template would override CAPEX's default sub-categories from
 * {Machinery, Vehicles, ...} to {Land, Design, Construction Phases, Permits}.
 * Empty for both v1 templates (Food Factory and Non-Food use CAPEX's defaults as-is).
 */
data class ModuleOverride(
    val subCategoryLabelsEn: List<String>,
    val subCategoryLabelsAr: List<String>
)

/**
 * A single industry-specific extra field a template adds on top of the generic schema.
 * fieldKey must be stable/unique per template (used as a lookup key when the UI renders
 * or the database persists this value inside a project's custom-fields JSON blob).
 */
data class CustomFieldDefinition(
    val fieldKey: String,
    val labelEn: String,
    val labelAr: String,
    val fieldType: CustomFieldType,
    val appliesToModule: ProjectModule,
    val isMandatory: Boolean
)

enum class CustomFieldType {
    TEXT,
    NUMBER,
    PERCENTAGE,
    DATE,
    BOOLEAN
}
