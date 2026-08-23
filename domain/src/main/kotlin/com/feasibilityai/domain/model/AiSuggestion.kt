package com.feasibilityai.domain.model

/**
 * An AI-suggested value, logged before it is ever written to a live entity table
 * (AI Rules §17 — "every AI-suggested value is written to AiSuggestionLog as pending,
 * never directly into a live entity table"). approvedBy is a device-local identifier
 * (the single Owner role — see SRS §4 User Roles) since v1 has no multi-user identity.
 */
data class AiSuggestion(
    val id: String,
    val projectId: String,
    val fieldPath: String,     // e.g. "rawMaterial.unitCost", "machinery[3].price"
    val provider: AiProviderType,
    val suggestedValueRaw: String, // provider's raw suggested value, parsed/typed by the consuming feature screen
    val status: AiSuggestionStatus,
    val approvedBy: String? = null,
    val createdAt: java.time.Instant,
    val resolvedAt: java.time.Instant? = null
) {
    init {
        require(fieldPath.isNotBlank()) { "fieldPath must not be blank." }
        if (status != AiSuggestionStatus.PENDING) {
            require(resolvedAt != null) { "resolvedAt must be set once status leaves PENDING." }
        }
    }
}

enum class AiSuggestionStatus { PENDING, APPROVED, REJECTED }

enum class AiProviderType {
    CHATGPT, CLAUDE, GEMINI, COPILOT, DEEPSEEK, GROK, QWEN, MISTRAL, CUSTOM
}
