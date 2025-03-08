package com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.tables

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afsoftwaresolutions.pruebaappinterrapidisimo.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TablesFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI(){

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tables, container, false)
    }

}