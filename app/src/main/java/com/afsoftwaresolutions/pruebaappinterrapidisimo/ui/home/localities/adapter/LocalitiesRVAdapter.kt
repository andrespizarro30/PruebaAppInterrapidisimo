package com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.home.localities.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afsoftwaresolutions.pruebaappinterrapidisimo.R
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.LocalitiesDataModel

class LocalitiesRVAdapter (
    private var localitiesList: List<LocalitiesDataModel> = emptyList()):
    RecyclerView.Adapter<LocalitiesRVViewHolder>()
{

    fun updateList(data: List<LocalitiesDataModel>){
        localitiesList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalitiesRVViewHolder {
        return LocalitiesRVViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.localities_item_list,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return localitiesList.size
    }

    override fun onBindViewHolder(holder: LocalitiesRVViewHolder, position: Int) {
        holder.render(localitiesList[position],position);
    }


}