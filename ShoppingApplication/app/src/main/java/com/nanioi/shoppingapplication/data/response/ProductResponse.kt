package com.nanioi.shoppingapplication.data.response

import com.nanioi.shoppingapplication.data.entity.product.ProductEntity
import java.util.*

data class ProductResponse(
    val id: String,
    val createdAt: Long,
    val productName: String,
    val productPrice: String,
    val productImage: String,
    val productType: String,
    val productIntroductionImage: String
) {

    fun toEntity(): ProductEntity =
        ProductEntity(
            createdAt = Date(createdAt),
            productName = productName,
            productPrice = productPrice.toDouble().toInt(),
            productType = productType,
            productImage = productImage,
            productIntroductionImage = productIntroductionImage,
            id = id.toLong()
        )

}