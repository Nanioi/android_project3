package com.nanioi.shoppingapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.nanioi.shoppingapplication.data.db.dao.ProductDao
import com.nanioi.shoppingapplication.data.entity.product.ProductEntity
import com.nanioi.shoppingapplication.utillity.DateConverter

@Database(
    entities = [ProductEntity::class],
    version = 1,
    exportSchema = false
)

//Date를 온전하게 받아 저장하기 위해 사용
@TypeConverters(DateConverter::class)
abstract class ProductDatabase: RoomDatabase() {

    companion object {
        const val DB_NAME = "ProductDataBase.db"
    }

    abstract fun productDao(): ProductDao

}
