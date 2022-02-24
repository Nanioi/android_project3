package com.nanioi.shoppingapplication.domain.product

import com.nanioi.shoppingapplication.data.repository.ProductRepository
import com.nanioi.shoppingapplication.domain.UseCase


internal class DeleteOrderedProductListUseCase(
    private val productRepository: ProductRepository
): UseCase {

    suspend operator fun invoke() {
        return productRepository.deleteAll()
    }

}
