package com.afsoftwaresolutions.pruebaappinterrapidisimo.data.core.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val headerData: HeaderData) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header("Usuario", headerData.getUser())
            .header("Identificacion", headerData.getId())
            .header("Accept", headerData.getAccept())
            .header("IdUsuario", headerData.getUserId())
            .header("IdCentroServicio", headerData.getServiceCenterId())
            .header("NombreCentroServicio", headerData.getServiceCenterName())
            .header("IdAplicativoOrigen", headerData.getAppOriginId())
            .header("Content-Type", headerData.getContextType())
            .build()

        return chain.proceed(request)
    }

}

//THIS MAY CHANGE ACCORDING TO USER BUT IT IS NOT SPECIFIED ON TEST DOCUMENTATION
//SO THAT'S WHY DATA IS BURNT
class HeaderData @Inject constructor(){

    fun getUser():String = "pam.meredy21"
    fun getId():String = "987204545"
    fun getAccept():String = "text/json"
    fun getUserId():String = "pam.meredy21"
    fun getServiceCenterId():String = "1295"
    fun getServiceCenterName():String = "PTO/BOGOTA/CUND/COL/OF PRINCIPAL - CRA30#7-45"
    fun getAppOriginId():String = "9"
    fun getContextType():String = "application/json"

}