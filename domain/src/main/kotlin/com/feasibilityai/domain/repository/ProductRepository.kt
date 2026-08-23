package com.feasibilityai.domain.repository

import com.feasibilityai.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun observeAllForProject(projectId: String): Flow<List<Product>>

    fun observeItem(itemId: String): Flow<Product?>

    suspend fun getItemOnce(itemId: String): Product?

    suspend fun upsertItem(item: Product): Product

    suspend fun deleteItem(itemId: String)
}
