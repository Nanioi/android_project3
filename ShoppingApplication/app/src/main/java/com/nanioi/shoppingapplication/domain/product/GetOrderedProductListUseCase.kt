package com.nanioi.shoppingapplication.domain.product

import com.nanioi.shoppingapplication.data.entity.product.ProductEntity
import com.nanioi.shoppingapplication.data.repository.ProductRepository
import com.nanioi.shoppingapplication.domain.UseCase

internal class GetOrderedProductListUseCase(
    private val productRepository: ProductRepository
): UseCase {

    suspend operator fun invoke(): List<ProductEntity> {
        return productRepository.getLocalProductList()
    }

}
