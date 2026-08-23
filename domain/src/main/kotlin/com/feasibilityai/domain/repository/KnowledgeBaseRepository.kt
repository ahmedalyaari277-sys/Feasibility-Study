package com.feasibilityai.domain.repository

import kotlinx.coroutines.flow.Flow

/**
 * Covers all 6 Knowledge Base library types (SRS §3, §19, screen §20.19). One
 * interface rather than 6 near-identical ones — LibraryType selects which table a
 * given call targets. Entries are represented as raw key-value maps here rather than
 * typed models, because each library type's field shape genuinely differs (a Supplier
 * has different fields than a Machine); typed access happens at the `data` module's
 * mapper layer, which knows how to interpret a given LibraryType's map shape.
 */
interface KnowledgeBaseRepository {

    fun observeEntries(type: LibraryType): Flow<List<LibraryEntry>>

    suspend fun getEntryOnce(type: LibraryType, entryId: String): LibraryEntry?

    suspend fun upsertEntry(type: LibraryType, entry: LibraryEntry): LibraryEntry

    suspend fun deleteEntry(type: LibraryType, entryId: String)
}

enum class LibraryType {
    MACHINES, RAW_MATERIALS, PACKAGING, SUPPLIERS, SALARY_TEMPLATES, BOM_TEMPLATES
}

/**
 * A generic Knowledge Base entry. `fields` holds the type-specific data as key-value
 * pairs; see each LibraryType's corresponding `core:database` entity for its actual
 * field set (e.g. SupplierEntity's fields vs. MachineLibraryEntity's fields differ).
 */
data class LibraryEntry(
    val id: String,
    val name: String,
    val fields: Map<String, String>
)
