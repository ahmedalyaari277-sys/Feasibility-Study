package com.feasibilityai.domain.repository

import com.feasibilityai.domain.model.Labor
import kotlinx.coroutines.flow.Flow

interface LaborRepository {

    fun observeAllForProject(projectId: String): Flow<List<Labor>>

    fun observeItem(itemId: String): Flow<Labor?>

    suspend fun getItemOnce(itemId: String): Labor?

    suspend fun upsertItem(item: Labor): Labor

    suspend fun deleteItem(itemId: String)

    /** Imports a SalaryTemplateLibraryEntity as a new Labor line (position + default salary/benefits/insurance prefilled, headcount left to the user). */
    suspend fun importFromSalaryTemplate(projectId: String, templateId: String): Labor
}
