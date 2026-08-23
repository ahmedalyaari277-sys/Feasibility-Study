package com.feasibilityai.domain.model

import java.time.Instant

/**
 * Root project model (SRS §2 FR-1, §9 Validation Rules, screen §20.9).
 *
 * Mandatory fields block navigation out of project creation until filled (§9).
 * Everything else in the app is "optional until Quality Check" — i.e. a Project
 * can exist with only these fields set, and every other module is populated later.
 */
data class Project(
    val id: String,

    // --- Mandatory (SRS §9) ---
    val name: String,
    val country: String,
    val baseCurrencyCode: String,
    val projectTypeId: String, // ProjectTypeTemplate.FOOD_FACTORY | NON_FOOD_MANUFACTURING
    val studyPeriodMonths: Int,
    val workingDaysPerYear: Int,
    val mode: ProjectMode,

    // --- Optional (SRS §9) ---
    val description: String? = null,
    val owner: String? = null,
    val location: String? = null,
    val category: String? = null,

    // --- System-managed ---
    val createdAt: Instant,
    val updatedAt: Instant
) {
    init {
        require(name.isNotBlank()) { "Project name is mandatory and cannot be blank (SRS §9)." }
        require(country.isNotBlank()) { "Project country is mandatory and cannot be blank (SRS §9)." }
        require(baseCurrencyCode.isNotBlank()) { "Project currency is mandatory and cannot be blank (SRS §9)." }
        require(projectTypeId.isNotBlank()) { "Project type is mandatory and cannot be blank (SRS §9)." }
        require(studyPeriodMonths > 0) { "Study period must be a positive number of months, got $studyPeriodMonths." }
        require(workingDaysPerYear in 1..366) { "Working days per year must be between 1 and 366, got $workingDaysPerYear." }
    }
}

/**
 * Manual vs AI-Assisted (SRS FR-1, screen §20.9). Chosen once at creation; AI-Assisted
 * routes into the AI Wizard sub-flow (§20.10-§20.12) before landing on Project Overview.
 * Choosing Manual — or exiting the AI Wizard early via "Skip AI, go Manual" — both leave
 * the project fully editable through ordinary Manual Mode from then on; this field is a
 * record of how the project *started*, not an ongoing restriction.
 */
enum class ProjectMode {
    MANUAL,
    AI_ASSISTED
}
