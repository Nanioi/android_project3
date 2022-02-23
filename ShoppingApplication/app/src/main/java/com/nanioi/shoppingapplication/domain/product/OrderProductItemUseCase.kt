package com.nanioi.shoppingapplication.domain.product

import com.nanioi.shoppingapplication.data.entity.product.ProductEntity
import com.nanioi.shoppingapplication.domain.UseCase
import com.nanioi.shoppingapplication.data.repository.ProductRepository


internal class OrderProductItemUseCase(
    private val productRepository: ProductRepository
): UseCase {

    suspend operator fun invoke(productEntity: ProductEntity): Long {
        return productRepository.insertProductItem(productEntity)
    }

}
