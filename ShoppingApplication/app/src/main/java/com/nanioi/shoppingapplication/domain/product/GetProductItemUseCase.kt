package com.nanioi.shoppingapplication.domain.product

import com.nanioi.shoppingapplication.data.entity.product.ProductEntity
import com.nanioi.shoppingapplication.domain.UseCase
import com.nanioi.shoppingapplication.repository.ProductRepository

internal class GetProductItemUseCase(
    private val productRepository: ProductRepository
): UseCase {

    suspend operator fun invoke(productId: Long): ProductEntity? {
        return productRepository.getProductItem(productId)
    }

}
