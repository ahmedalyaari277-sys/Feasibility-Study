package com.feasibilityai.domain.repository

import com.feasibilityai.domain.model.RawMaterial
import kotlinx.coroutines.flow.Flow

interface RawMaterialRepository {

    fun observeAllForProject(projectId: String): Flow<List<RawMaterial>>

    fun observeItem(itemId: String): Flow<RawMaterial?>

    suspend fun getItemOnce(itemId: String): RawMaterial?

    suspend fun upsertItem(item: RawMaterial): RawMaterial

    suspend fun deleteItem(itemId: String)

    /**
     * Copies a Knowledge Base RawMaterialLibraryEntity into this project as a new
     * RawMaterial (SRS §12 BOM Rules — "applying a template copies values into the
     * project; it does not create a live link back to the template"). Returns the
     * newly created project-scoped RawMaterial.
     */
    suspend fun importFromLibrary(projectId: String, libraryEntryId: String): RawMaterial
}
