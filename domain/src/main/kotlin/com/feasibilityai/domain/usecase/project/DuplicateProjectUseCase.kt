package com.feasibilityai.domain.usecase.project

import com.feasibilityai.domain.model.Project
import com.feasibilityai.domain.repository.ProjectRepository
import javax.inject.Inject

/**
 * Duplicates a project (SRS §20.8 Home screen "Duplicate" action).
 *
 * Reports are intentionally NOT duplicated — see ProjectRepository.duplicateProject
 * doc comment and SRS §18 (report snapshot immutability). Everything else under the
 * source project (CAPEX, OPEX, Raw Materials, Packaging, BOM, Machinery, Labor,
 * Utilities, Products, Sales Forecast, Financing, Tax Profile, Scenarios, Documents,
 * AiSuggestionLog) is deep-copied to the new project id at the database layer.
 *
 * The new project's name is validated here (not left to the Project constructor alone)
 * so a bad name fails fast before any copy work is attempted.
 */
class DuplicateProjectUseCase @Inject constructor(
    private val projectRepository: ProjectRepository
) {

    suspend operator fun invoke(sourceProjectId: String, newName: String): Result<Project> = runCatching {
        require(sourceProjectId.isNotBlank()) { "sourceProjectId must not be blank." }
        require(newName.isNotBlank()) { "newName must not be blank (SRS §9 — Project name is mandatory)." }

        val trimmedName = newName.trim()
        projectRepository.duplicateProject(sourceProjectId, trimmedName)
    }
}
