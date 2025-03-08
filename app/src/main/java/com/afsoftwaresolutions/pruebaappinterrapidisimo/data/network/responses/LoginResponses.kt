package com.afsoftwaresolutions.pruebaappinterrapidisimo.data.network.responses

import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.UserDataModel
import com.google.gson.annotations.SerializedName

data class LoginResponse (

    @SerializedName("Usuario") var Usuario: String? = null,
    @SerializedName("Identificacion") var Identificacion: String,
    @SerializedName("Nombre") var Nombre: String? = null,
    @SerializedName("Apellido8") var Apellido8: String? = null,
    @SerializedName("Apellido2") var Apellido2: String? = null,
    @SerializedName("Cargo") var Cargo: String? = null,
    @SerializedName("Aplicaciones") var Aplicaciones: Aplicaciones? = Aplicaciones(),
    @SerializedName("Ubicaciones") var Ubicaciones: ArrayList<Ubicaciones> = arrayListOf(),
    @SerializedName("MensajeResultado") var MensajeResultado : Int? = null,
    @SerializedName("IdLocalidad") var IdLocalidad: String? = null,
    @SerializedName("NombreLocalidad") var NombreLocalidad: String? = null,
    @SerializedName("NomRol") var NomRol: String? = null,
    @SerializedName("IdRol") var IdRol: String? = null,
    @SerializedName("TokenJWT") var TokenJWT: String? = null,
    @SerializedName("ModulosApp") var ModulosApp: ArrayList<ModulosApp>  = arrayListOf()

){
    fun toDomain(): UserDataModel{
        return UserDataModel(
            Usuario = Usuario,
            Identificacion = Identificacion,
            Nombre = Nombre,
            user = "",
            password = ""
        )
    }
}

data class Aplicaciones (

    @SerializedName("APLIdAplicacion") var APLIdAplicacion: Int? = null,
    @SerializedName("APLNombreAplicacion") var APLNombreAplicacion: String? = null,
    @SerializedName("APLNombreCorto") var APLNombreCorto: String? = null,
    @SerializedName("APLVersion") var APLVersion: String? = null,
    @SerializedName("APLEstado") var APLEstado: Boolean? = null,
    @SerializedName("APLRutaAplicacion") var APLRutaAplicacion: String? = null,
    @SerializedName("Paginas") var Paginas: Int? = null,
    @SerializedName("Modulos") var Modulos: ArrayList<Modulos> = arrayListOf(),
    @SerializedName("TotalPaginas") var TotalPaginas: Int? = null

)

data class Modulos (

    @SerializedName("MODIdModulo") var MODIdModulo: Int? = null,
    @SerializedName("MODNombre") var MODNombre: String? = null,
    @SerializedName("MODNombreCorto") var MODNombreCorto: String? = null,
    @SerializedName("MODEstado") var MODEstado: Boolean? = null,
    @SerializedName("MODIdAplicacion") var MODIdAplicacion: Int? = null,
    @SerializedName("APLNombreAplicacion") var APLNombreAplicacion: String? = null,
    @SerializedName("Menus") var Menus: ArrayList<Menus> = arrayListOf(),
    @SerializedName("TotalPaginas") var TotalPaginas: Int? = null

)

data class ModulosApp (

    @SerializedName("MODIdModulo") var MODIdModulo: Int? = null,
    @SerializedName("MODNombre") var MODNombre: String? = null,
    @SerializedName("MODEstado") var MODEstado: Boolean? = null,
    @SerializedName("MODVisible") var MODVisible: Boolean? = null,
    @SerializedName("MODOrden") var MODOrden: Int? = null,
    @SerializedName("MODIdAplicacion") var MODIdAplicacion: Int? = null

)

data class Menus (

    @SerializedName("MENIdMenu") var MENIdMenu: Int? = null,
    @SerializedName("MENNombre") var MENNombre: String? = null,
    @SerializedName("MENNombreCorto") var MENNombreCorto: String? = null,
    @SerializedName("MENEstado") var MENEstado: Boolean? = null,
    @SerializedName("MENIdModulo") var MENIdModulo: Int? = null,
    @SerializedName("MENIdMenuPadre") var MENIdMenuPadre: Int? = null,
    @SerializedName("MENUrl") var MENUrl: String? = null,
    @SerializedName("MENObservacion") var MENObservacion: String? = null,
    @SerializedName("MENIcono") var MENIcono: String? = null,
    @SerializedName("APLNombreAplicacion") var APLNombreAplicacion: String? = null,
    @SerializedName("MODNombre") var MODNombre: String? = null,
    @SerializedName("SubMenu") var SubMenu: ArrayList<String> = arrayListOf(),
    @SerializedName("AccionesMenu") var AccionesMenu: ArrayList<AccionesMenu> = arrayListOf(),
    @SerializedName("TotalPaginas") var TotalPaginas: Int? = null

)

data class AccionesMenu (

    @SerializedName("MEAIdMenuAccion") var MEAIdMenuAccion: Int? = null,
    @SerializedName("MEAIdMenu") var MEAIdMenu: Int? = null,
    @SerializedName("MEAIdAccion") var MEAIdAccion: Int? = null,
    @SerializedName("MEAEstado") var MEAEstado: Boolean? = null,
    @SerializedName("ACCNombre") var ACCNombre: String?  = null

)

data class Ubicaciones (

    @SerializedName("UBUIdUsuario") var UBUIdUsuario: String? = null,
    @SerializedName("UBUIdCentroServicios") var UBUIdCentroServicios: Int? = null,
    @SerializedName("NombreCentroServicios") var NombreCentroServicios: String? = null,
    @SerializedName("IdCaja") var IdCaja: Int? = null,
    @SerializedName("IdLocalidad") var IdLocalidad: String? = null,
    @SerializedName("NombreCompletoLocalidad") var NombreCompletoLocalidad: String? = null

)