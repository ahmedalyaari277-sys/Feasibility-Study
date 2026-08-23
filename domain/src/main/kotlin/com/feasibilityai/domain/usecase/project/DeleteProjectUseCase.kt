package com.feasibilityai.domain.usecase.project

import com.feasibilityai.domain.repository.ProjectRepository
import javax.inject.Inject

/**
 * Deletes a project (SRS §20.8 Home screen, §5 Permissions — confirmation dialog is
 * already handled by the caller before this executes).
 *
 * Cascading deletion of dependent entities (CAPEX, OPEX, Raw Materials, BOM entries,
 * Reports, Documents, etc.) happens at the `core:database` layer via Room foreign-key
 * ON DELETE CASCADE — this use case does not enumerate or delete child records itself,
 * since duplicating that knowledge here would let the two layers drift out of sync.
 *
 * Note: deleting a project does NOT delete Knowledge Base entries the project referenced
 * (Machines/Materials/Suppliers/Templates libraries) — those are cross-project data by
 * design (SRS §3, §19) and must survive the deletion of any single project that used them.
 */
class DeleteProjectUseCase @Inject constructor(
    private val projectRepository: ProjectRepository
) {

    suspend operator fun invoke(projectId: String): Result<Unit> = runCatching {
        require(projectId.isNotBlank()) { "projectId must not be blank." }
        projectRepository.deleteProject(projectId)
    }
}
