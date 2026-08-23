package com.feasibilityai.domain.usecase.capex

import com.feasibilityai.domain.repository.CapexRepository
import javax.inject.Inject

/**
 * Deletes a CapexItem (screen §20.1/§20.2 Delete action). Confirmation dialog
 * (§9 Validation Rules — "deleting an entity with dependent records requires
 * confirmation naming what will be affected") is a UI-layer concern handled before
 * this use case is invoked.
 */
class DeleteCapexItemUseCase @Inject constructor(
    private val capexRepository: CapexRepository
) {

    suspend operator fun invoke(itemId: String): Result<Unit> = runCatching {
        require(itemId.isNotBlank()) { "itemId must not be blank." }
        capexRepository.deleteItem(itemId)
    }
}
