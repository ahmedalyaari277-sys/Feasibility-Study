package com.feasibilityai.domain.model

/**
 * An attached document (Document Management Rules §19). No in-app editing —
 * view/attach/detach/replace only; editing happens in the source app.
 */
data class Document(
    val id: String,
    val projectId: String,
    val relatedEntityId: String? = null, // e.g. a MachineryId, if this quote/invoice belongs to a specific machine rather than the project generally
    val name: String,
    val type: DocumentType,
    val filePath: String,
    val uploadedAt: java.time.Instant
) {
    init {
        require(name.isNotBlank()) { "Document name must not be blank." }
        require(filePath.isNotBlank()) { "Document filePath must not be blank." }
    }
}

enum class DocumentType {
    PDF, EXCEL, WORD, POWERPOINT, IMAGE, ZIP, CAD, TECHNICAL_FILE,
    SUPPLIER_QUOTE, INVOICE, CONTRACT, STUDY
}
