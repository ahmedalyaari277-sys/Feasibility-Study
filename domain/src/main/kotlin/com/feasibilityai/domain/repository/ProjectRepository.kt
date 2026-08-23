package com.feasibilityai.domain.repository

import com.feasibilityai.domain.model.Project
import kotlinx.coroutines.flow.Flow

/**
 * Contract for Project persistence. Implemented by `data:ProjectRepositoryImpl`,
 * consumed by `domain:usecase/project/*` use cases and (indirectly, through those
 * use cases) by `feature:home` and `feature:project` view models.
 *
 * All reads are exposed as Flow so the Home / Project List Dashboard (screen §20.8)
 * and Project Overview (screen §20.13) update live when data changes underneath them —
 * no manual refresh calls needed anywhere in the UI layer.
 */
interface ProjectRepository {

    /** All projects, for the Home / Project List Dashboard (§20.8). Ordered by most-recently-updated first. */
    fun observeAllProjects(): Flow<List<Project>>

    /** A single project by id, for Project Overview (§20.13) and anywhere else that needs to react to that one project's changes. Emits null if the project has been deleted. */
    fun observeProject(projectId: String): Flow<Project?>

    /** One-shot fetch, for use cases that need a snapshot rather than a live stream (e.g. report generation, per §18 snapshot immutability — the use case reads once, not via Flow, so the snapshot can't shift mid-generation). */
    suspend fun getProjectOnce(projectId: String): Project?

    /** Inserts a new project or updates an existing one (same id). Returns the persisted project. */
    suspend fun upsertProject(project: Project): Project

    /**
     * Deletes a project and all of its dependent data (CAPEX, OPEX, BOM entries, etc. —
     * cascading delete is a `core:database` foreign-key concern, not something this
     * interface needs to know about). Confirmation dialog is a UI-layer concern (§9);
     * by the time this is called, the user has already confirmed.
     */
    suspend fun deleteProject(projectId: String)

    /**
     * Deep-copies a project (all entities under it) to a new project id/name, per
     * FR "Duplicate" action on Home (§20.8) and DuplicateProjectUseCase. Reports are
     * NOT duplicated — a duplicated project starts with zero reports, since a report is
     * an immutable snapshot of a specific project's history (§18), not something that
     * makes sense to copy onto a different project.
     */
    suspend fun duplicateProject(sourceProjectId: String, newName: String): Project
}
