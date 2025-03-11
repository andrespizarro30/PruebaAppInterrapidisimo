package com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.helpers

import android.R.id.input
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


object DateFormater {

    fun formatToYYYYMMSS_HHMMSS(input:String) : String{

        val dateTime = LocalDateTime.parse(input)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val formattedDate = dateTime.format(formatter)

        return formattedDate

    }


}