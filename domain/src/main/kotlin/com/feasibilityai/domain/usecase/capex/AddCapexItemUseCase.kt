package com.feasibilityai.domain.usecase.capex

import com.feasibilityai.domain.model.CapexItem
import com.feasibilityai.domain.repository.CapexRepository
import java.util.UUID
import javax.inject.Inject

/**
 * Creates a new CapexItem (screen §20.2 CAPEX Detail/Edit "Save", new-item path).
 * All field validation happens in the CapexItem constructor itself (fail-fast,
 * consistent with every other model in this codebase) — this use case's only added
 * responsibility is id assignment, so the UI never has to generate/track ids itself.
 */
class AddCapexItemUseCase @Inject constructor(
    private val capexRepository: CapexRepository
) {

    suspend operator fun invoke(input: Input): Result<CapexItem> = runCatching {
        val item = CapexItem(
            id = UUID.randomUUID().toString(),
            projectId = input.projectId,
            category = input.category.trim(),
            name = input.name.trim(),
            value = input.value,
            supplierId = input.supplierId,
            description = input.description?.trim()?.ifBlank { null },
            attachmentIds = input.attachmentIds
        )
        capexRepository.upsertItem(item)
    }

    data class Input(
        val projectId: String,
        val category: String,
        val name: String,
        val value: com.feasibilityai.domain.model.MoneyValue,
        val supplierId: String? = null,
        val description: String? = null,
        val attachmentIds: List<String> = emptyList()
    )
}
