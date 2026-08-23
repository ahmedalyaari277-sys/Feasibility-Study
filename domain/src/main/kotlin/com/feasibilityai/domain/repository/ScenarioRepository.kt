package com.feasibilityai.domain.repository

import com.feasibilityai.domain.model.Scenario
import kotlinx.coroutines.flow.Flow

interface ScenarioRepository {

    fun observeAllForProject(projectId: String): Flow<List<Scenario>>

    fun observeItem(itemId: String): Flow<Scenario?>

    suspend fun getItemOnce(itemId: String): Scenario?

    suspend fun upsertItem(item: Scenario): Scenario

    /** Custom scenarios only may be deleted — built-in ScenarioType values (CONSERVATIVE/EXPECTED/OPTIMISTIC) are permanent per project; implementations must reject deletion of a non-CUSTOM scenario. */
    suspend fun deleteItem(itemId: String)

    /** Creates the 3 built-in scenarios for a newly created project (SRS §5). Called once, from CreateProjectUseCase's completion, not exposed as a user-facing action. */
    suspend fun seedBuiltInScenarios(projectId: String): List<Scenario>
}
