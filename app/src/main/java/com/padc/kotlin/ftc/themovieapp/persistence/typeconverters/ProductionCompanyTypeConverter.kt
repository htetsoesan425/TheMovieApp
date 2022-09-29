package com.padc.kotlin.ftc.themovieapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padc.kotlin.ftc.themovieapp.data.vos.ProductionCompanyVO

class ProductionCompanyTypeConverter {
    @TypeConverter
    fun toString(productionCompanies: List<ProductionCompanyVO>?): String {
        return Gson().toJson(productionCompanies)
    }

    @TypeConverter
    fun toProductionCompanies(productionCompaniesJsonStr: String): List<ProductionCompanyVO>? {
        val productionCompanyVOType = object : TypeToken<List<ProductionCompanyVO>?>() {}.type
        return Gson().fromJson(productionCompaniesJsonStr, productionCompanyVOType)
    }
}