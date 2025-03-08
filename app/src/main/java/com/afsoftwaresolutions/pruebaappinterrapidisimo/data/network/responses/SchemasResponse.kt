package com.afsoftwaresolutions.pruebaappinterrapidisimo.data.network.responses

import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.SchemasDataModel
import com.google.gson.annotations.SerializedName

data class SchemasResponse(
    @SerializedName("TableName") var TableName: String,
){
    fun toDomain():SchemasDataModel{
        return SchemasDataModel(
            TableName = TableName
        )
    }
}