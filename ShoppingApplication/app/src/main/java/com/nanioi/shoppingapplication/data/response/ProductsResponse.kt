package com.nanioi.shoppingapplication.data.response

data class ProductsResponse(
    val items: List<ProductResponse>,
    val count: Int
)
