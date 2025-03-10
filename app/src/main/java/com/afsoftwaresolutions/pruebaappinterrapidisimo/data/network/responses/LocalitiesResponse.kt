package com.afsoftwaresolutions.pruebaappinterrapidisimo.data.network.responses

import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.LocalitiesDataModel
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.UserDataModel
import com.google.gson.annotations.SerializedName

data class LocalitiesResponse (

    @SerializedName("IdLocalidad") var IdLocalidad: String,
    @SerializedName("IdTipoLocalidad") var IdTipoLocalidad: String? = null,
    @SerializedName("IdAncestroPGrado") var IdAncestroPGrado: String? = null,
    @SerializedName("IdAncestroSGrado") var IdAncestroSGrado: String? = null,
    @SerializedName("NombreAncestroSGrado") var NombreAncestroSGrado: String? = null,
    @SerializedName("IdAncestroTGrado") var IdAncestroTGrado: String? = null,
    @SerializedName("NombreAncestroTGrado") var NombreAncestroTGrado: String? = null,
    @SerializedName("Nombre") var Nombre: String? = null,
    @SerializedName("NombreCorto") var NombreCorto: String? = null,
    @SerializedName("NombreAncestroPGrado") var NombreAncestroPGrado: String? = null,
    @SerializedName("NombreCompleto") var NombreCompleto: String? = null,
    @SerializedName("NombreTipoLocalidad") var NombreTipoLocalidad: String? = null,
    @SerializedName("AsignadoEnZona") var AsignadoEnZona: Boolean? = null,
    @SerializedName("AsignadoEnZonaOrig") var AsignadoEnZonaOrig: Boolean? = null,
    @SerializedName("DispoLocalidad") var DispoLocalidad: Boolean? = null,
    @SerializedName("NombreZona") var NombreZona: String? = null,
    @SerializedName("CodigoPostal") var CodigoPostal: String? = null,
    @SerializedName("Indicativo") var Indicativo: String? = null,
    @SerializedName("IdCentroServicio") var IdCentroServicio: Int? = null,
    @SerializedName("EstadoRegistro") var EstadoRegistro: Int? = null,
    @SerializedName("TiposLocalidades") var TiposLocalidades: String? = null,
    @SerializedName("PermiteRecogida") var PermiteRecogida: Boolean? = null,
    @SerializedName("HoraMaxRecogida") var HoraMaxRecogida: Int? = null,
    @SerializedName("SeGeorreferencia") var SeGeorreferencia: Boolean? = null,
    @SerializedName("ValorRecogida") var ValorRecogida: Double? = null,
    @SerializedName("PermitePreEnviosPunto") var PermitePreEnviosPunto: Boolean? = null,
    @SerializedName("EtiquetaEntrega") var EtiquetaEntrega: Boolean? = null,
    @SerializedName("HoraMinRecogida") var HoraMinRecogida: Int? = null,
    @SerializedName("AbreviacionCiudad") var AbreviacionCiudad: String? = null,

){
    fun toDomain(): LocalitiesDataModel {
        return LocalitiesDataModel(
            IdLocalidad = IdLocalidad,
            Nombre = Nombre,
            AbreviacionCiudad = AbreviacionCiudad
        )
    }
}