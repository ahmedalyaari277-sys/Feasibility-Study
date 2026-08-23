package com.feasibilityai.domain.repository

import com.feasibilityai.domain.model.AiProviderType
import kotlinx.coroutines.flow.Flow

/**
 * Contract for AI provider configuration (§17, screen §20.21). Read methods return
 * AiProviderConfig with a masked credential only — the real API key lives in
 * core:security's Keystore-backed ApiKeyVault and is never round-tripped through this
 * interface as plaintext. setCredential is the only method that accepts a real key,
 * and it writes straight through to the vault at the `data` implementation layer.
 */
interface AiProviderRepository {

    fun observeConfiguredProviders(): Flow<List<AiProviderConfig>>

    suspend fun getConfig(provider: AiProviderType): AiProviderConfig?

    /** Sets/replaces the real API key (or, for CUSTOM, the full connection config) for a provider. The plaintext key never returns from this repository afterward — only maskedCredential does. */
    suspend fun setCredential(provider: AiProviderType, credential: ProviderCredential)

    suspend fun setEnabled(provider: AiProviderType, enabled: Boolean)

    suspend fun removeProvider(provider: AiProviderType)
}

data class AiProviderConfig(
    val provider: AiProviderType,
    val isEnabled: Boolean,
    val maskedCredential: String, // e.g. "sk-...a91f" — never the full key
    val customEndpointUrl: String? = null, // set only when provider == CUSTOM
    val customAuthMethod: String? = null   // set only when provider == CUSTOM
)

data class ProviderCredential(
    val apiKey: String,
    val customEndpointUrl: String? = null,
    val customAuthMethod: String? = null
)
