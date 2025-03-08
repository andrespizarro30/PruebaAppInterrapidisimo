package com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.home.tables.adapter

import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.afsoftwaresolutions.pruebaappinterrapidisimo.databinding.LocalitiesItemListBinding
import com.afsoftwaresolutions.pruebaappinterrapidisimo.databinding.TablesItemListBinding
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.LocalitiesDataModel
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.SchemasDataModel

class TablesRVViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = TablesItemListBinding.bind(view)

    fun render(schemaData: SchemasDataModel, posit : Int){
        val context = binding.tvTableName

        binding.tvTableName.text = schemaData.TableName

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