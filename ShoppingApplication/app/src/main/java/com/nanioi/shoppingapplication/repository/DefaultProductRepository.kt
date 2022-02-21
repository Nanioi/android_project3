package com.nanioi.shoppingapplication.repository

import com.nanioi.shoppingapplication.data.entity.product.ProductEntity
import com.nanioi.shoppingapplication.data.network.ProductApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultProductRepository(
        private val productApi: ProductApiService,
        private val ioDispatcher: CoroutineDispatcher
): ProductRepository {

    override suspend fun getProductList(): List<ProductEntity>  = withContext(ioDispatcher){
        TODO("Not yet implemented")
    }
    override suspend fun getLocalProductList(): List<ProductEntity>  = withContext(ioDispatcher) {
        TODO("Not yet implemented")
    }

    override suspend fun insertProductItem(ProductItem: ProductEntity): Long  = withContext(ioDispatcher){
        TODO("Not yet implemented")
    }

    override suspend fun insertProductList(ProductList: List<ProductEntity>)  = withContext(ioDispatcher){
        TODO("Not yet implemented")
    }

    override suspend fun updateProductItem(ProductItem: ProductEntity)  = withContext(ioDispatcher){
        TODO("Not yet implemented")
    }

    override suspend fun getProductItem(itemId: Long): ProductEntity?   = withContext(ioDispatcher){
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll()  = withContext(ioDispatcher){
        TODO("Not yet implemented")
    }

    override suspend fun deleteProductItem(id: Long)  = withContext(ioDispatcher){
        TODO("Not yet implemented")
    }


}
// api 기반으로 불러오기 때문에 코루틴을 이용해서 ioDispatcher에 쓸 수 있도록 만들고 ProductApiservice가져올 것