package com.afsoftwaresolutions.pruebaappinterrapidisimo.data.network.responses

import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.SchemasDataModel
import com.google.gson.annotations.SerializedName

data class SchemasResponse(
    @SerializedName("NombreTabla") val nombreTabla: String,
    @SerializedName("Pk") val pk: String,
    @SerializedName("QueryCreacion") val queryCreacion: String,
    @SerializedName("BatchSize") val batchSize: Long,
    @SerializedName("Filtro") val filtro: String,
    @SerializedName("Error") val error: Any?,
    @SerializedName("NumeroCampos") val numeroCampos: Long,
    @SerializedName("MetodoApp") val metodoApp: Any?,
    @SerializedName("FechaActualizacionSincro") val fechaActualizacionSincro: String
){
    fun toDomain():SchemasDataModel{
        return SchemasDataModel(
            TableName = nombreTabla
        )
    }
}