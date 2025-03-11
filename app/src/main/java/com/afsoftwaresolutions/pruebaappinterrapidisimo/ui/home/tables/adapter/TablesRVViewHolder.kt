package com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.home.tables.adapter

import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.afsoftwaresolutions.pruebaappinterrapidisimo.databinding.LocalitiesItemListBinding
import com.afsoftwaresolutions.pruebaappinterrapidisimo.databinding.TablesItemListBinding
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.LocalitiesDataModel
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.SchemasDataModel
import com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.helpers.DateFormater

class TablesRVViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = TablesItemListBinding.bind(view)

    fun render(schemaData: SchemasDataModel){
        val context = binding.tvTableName

        binding.tvTableName.text = schemaData.TableName
        binding.tvPrimaryKey.text = buildString {
            append("PK: ")
            append(schemaData.pk)
        }
        binding.tvNumeroCampos.text = buildString {
            append("Fields: ")
            append(schemaData.numeroCampos.toString())
        }
        binding.tvFechaActualizacion.text = buildString {
            append("Updated: ")
            append(DateFormater.formatToYYYYMMSS_HHMMSS(schemaData.fechaActualizacionSincro))
        }

        binding.parent.setOnClickListener {
            startRotationAnimation(binding.parent)
        }
    }

    private fun startRotationAnimation(view: View){
        view.animate().apply {
            duration = 500
            interpolator = LinearInterpolator()
            rotationXBy(360f) // Rotate on X-axis
            start()
        }


    }

}