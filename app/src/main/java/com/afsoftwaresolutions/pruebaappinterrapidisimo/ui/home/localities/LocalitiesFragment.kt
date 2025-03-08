package com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.home.localities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.afsoftwaresolutions.pruebaappinterrapidisimo.R
import com.afsoftwaresolutions.pruebaappinterrapidisimo.databinding.FragmentLocalitiesBinding
import com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.helpers.DialogHelper
import com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.home.HomeStates
import com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.home.HomeViewModel
import com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.home.localities.adapter.LocalitiesRVAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LocalitiesFragment : Fragment() {

    private val homeViewModel by activityViewModels<HomeViewModel>()

    private var _binding: FragmentLocalitiesBinding? = null
    private val binding get() = _binding!!

    private lateinit var localitiesRVAdapter: LocalitiesRVAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI(){
        initList()
        initViewModelListener()
    }

    private fun initList(){
        localitiesRVAdapter = LocalitiesRVAdapter()
        binding.rvLocalities.apply {
            layoutManager = LinearLayoutManager(context)
            adapter=localitiesRVAdapter
        }
    }

    private fun initViewModelListener(){

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                homeViewModel.stateLocalitiesData.collect{state->
                    when(state){
                        HomeStates.Loading -> loadingState()
                        is HomeStates.ErrorLocalitiesData -> errorState(state)
                        is HomeStates.SuccessLocalitiesData -> successState(state)
                        else ->{}
                    }
                }
            }
        }

    }

    private fun loadingState(){
        binding.pb.isVisible = true
    }

    private fun errorState(state: HomeStates.ErrorLocalitiesData){
        binding.pb.isVisible = false
        DialogHelper.showDialog(requireContext(),getString(R.string.fetch_error),state.error)
    }


    private fun successState(state: HomeStates.SuccessLocalitiesData){
        binding.pb.isVisible = false
        localitiesRVAdapter.updateList(state.localities)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocalitiesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}