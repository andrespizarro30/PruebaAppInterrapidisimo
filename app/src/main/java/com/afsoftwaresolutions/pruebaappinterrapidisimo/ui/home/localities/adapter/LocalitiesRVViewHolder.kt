package com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.home.localities.adapter

import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.afsoftwaresolutions.pruebaappinterrapidisimo.databinding.LocalitiesItemListBinding
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.LocalitiesDataModel

class LocalitiesRVViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = LocalitiesItemListBinding.bind(view)

    fun render(localitiesData: LocalitiesDataModel, posit : Int){
        val context = binding.tvNombreCiudad.context

        binding.tvAbreviacionCiudad.text = localitiesData.AbreviacionCiudad
        binding.tvNombreCiudad.text = localitiesData.Nombre

        binding.parent.setOnClickListener {
            startRotationAnimation(binding.parent)
        }
    }

    private fun startRotationAnimation(view:View){
        view.animate().apply {
            duration = 500
            interpolator = LinearInterpolator()
            rotationXBy(360f) // Rotate on X-axis
            start()
        }


    }

}