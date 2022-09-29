package com.padc.kotlin.ftc.themovieapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padc.kotlin.ftc.themovieapp.data.vos.ProductionCountryVO

class ProductionCountryTypeConverter {
    @TypeConverter
    fun toString(productionCountries: List<ProductionCountryVO>?): String {
        return Gson().toJson(productionCountries)
    }

    @TypeConverter
    fun toProductionCountries(productionCountryJsonStr: String): List<ProductionCountryVO>? {
        val productionCountryVOType = object : TypeToken<List<ProductionCountryVO>?>() {}.type
        return Gson().fromJson(productionCountryJsonStr, productionCountryVOType)
    }
}