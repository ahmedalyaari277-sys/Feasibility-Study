package com.feasibilityai.domain.usecase.project

import com.feasibilityai.domain.model.Project
import com.feasibilityai.domain.model.ProjectMode
import com.feasibilityai.domain.repository.ProjectRepository
import java.time.Instant
import java.util.UUID
import javax.inject.Inject

/**
 * Creates a new Project from the New Project Wizard's Type & Mode Selection screen
 * (§20.9). All mandatory-field validation (name/country/currency/type/study period/
 * working days non-blank and in-range) happens inside the Project constructor itself
 * (SRS §9) — this use case's job is only to assign identity/timestamps and persist.
 *
 * Deliberately does NOT decide whether to route into the AI Wizard sub-flow — that's
 * a navigation decision made by the caller (feature:project's NewProjectViewModel)
 * based on the returned Project's `mode`. Keeping that decision out of the use case
 * keeps this class testable without any navigation/UI dependencies.
 */
class CreateProjectUseCase @Inject constructor(
    private val projectRepository: ProjectRepository
) {

    suspend operator fun invoke(input: Input): Result<Project> = runCatching {
        val now = Instant.now()
        val project = Project(
            id = UUID.randomUUID().toString(),
            name = input.name.trim(),
            country = input.country.trim(),
            baseCurrencyCode = input.baseCurrencyCode.trim().uppercase(),
            projectTypeId = input.projectTypeId,
            studyPeriodMonths = input.studyPeriodMonths,
            workingDaysPerYear = input.workingDaysPerYear,
            mode = input.mode,
            description = input.description?.trim()?.ifBlank { null },
            owner = input.owner?.trim()?.ifBlank { null },
            location = input.location?.trim()?.ifBlank { null },
            category = input.category?.trim()?.ifBlank { null },
            createdAt = now,
            updatedAt = now
        )
        projectRepository.upsertProject(project)
    }

    /**
     * Input DTO mirrors exactly the mandatory/optional fields from screen §20.9 —
     * kept separate from the domain Project model so the UI layer never has to
     * construct a Project directly (and can't accidentally set createdAt/updatedAt/id).
     */
    data class Input(
        val name: String,
        val country: String,
        val baseCurrencyCode: String,
        val projectTypeId: String,
        val studyPeriodMonths: Int,
        val workingDaysPerYear: Int,
        val mode: ProjectMode,
        val description: String? = null,
        val owner: String? = null,
        val location: String? = null,
        val category: String? = null
    )
}
