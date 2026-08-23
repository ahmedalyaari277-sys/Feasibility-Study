package com.feasibilityai.domain.repository

import com.feasibilityai.domain.model.Document
import kotlinx.coroutines.flow.Flow

interface DocumentRepository {

    /** All documents for a project, regardless of whether they're attached to a specific entity or the project generally. */
    fun observeAllForProject(projectId: String): Flow<List<Document>>

    /** Documents attached to one specific entity (e.g. one Machinery record's quotes), for that entity's detail screen. */
    fun observeForEntity(relatedEntityId: String): Flow<List<Document>>

    suspend fun upsertDocument(document: Document): Document

    suspend fun deleteDocument(documentId: String)
}
