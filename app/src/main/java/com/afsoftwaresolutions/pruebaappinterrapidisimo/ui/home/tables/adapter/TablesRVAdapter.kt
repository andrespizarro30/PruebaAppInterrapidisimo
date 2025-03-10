package com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.home.tables.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afsoftwaresolutions.pruebaappinterrapidisimo.R
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.SchemasDataModel

class TablesRVAdapter (
    private var schemasList: List<SchemasDataModel> = emptyList()):
    RecyclerView.Adapter<TablesRVViewHolder>()
{

    fun updateList(data: List<SchemasDataModel>){
        schemasList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TablesRVViewHolder {
        return TablesRVViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.tables_item_list,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return schemasList.size
    }

    override fun onBindViewHolder(holder: TablesRVViewHolder, position: Int) {
        holder.render(schemasList[position]);
    }


}