package com.assessment.cleanarchitecture.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.assessment.cleanarchitecture.R
import com.assessment.cleanarchitecture.data.model.Country
import com.assessment.cleanarchitecture.databinding.FragmentCountryListBinding
import com.assessment.cleanarchitecture.presentation.adapter.CountryListAdapter
import com.assessment.cleanarchitecture.presentation.viewmodel.ListCountryViewModel
import com.assessment.cleanarchitecture.utils.ResponseHandler
import com.assessment.cleanarchitecture.utils.gone
import com.assessment.cleanarchitecture.utils.showSnackBar
import com.assessment.cleanarchitecture.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ListCountryFragment : Fragment() {

    private var _binding: FragmentCountryListBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var countryListAdapter: CountryListAdapter

    companion object {
        fun newInstance() = ListCountryFragment()
    }

    /*Get the viewModel instance*/
    private val viewModel by lazy {
        ViewModelProvider(this)[ListCountryViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountryListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        Log.d("TESTING", "onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
        Log.d("TESTING", "onViewCreated")
    }

    /*Configure the UI elements*/
    private fun initView() {
        binding.rvCountries.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(25)
            adapter = countryListAdapter
        }
    }

    /*Observe the flow elements*/
    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect {
                handleUiState(it)
            }
        }
    }

    /*Update the UI state based on response*/
    private fun handleUiState(uiState: ResponseHandler<List<Country>>) {
        when (uiState) {
            is ResponseHandler.Loading -> {
                binding.progressbar.visible()
                binding.rvCountries.gone()
                binding.textViewFailed.gone()
            }

            is ResponseHandler.Success -> {
                binding.progressbar.gone()
                binding.rvCountries.visible()
                binding.textViewFailed.gone()
                uiState.data?.let { countryListAdapter.setData(it) }
            }

            is ResponseHandler.Error -> {
                binding.progressbar.gone()
                binding.rvCountries.gone()
                binding.textViewFailed.visible()
                binding.textViewFailed.text = uiState.message
                binding.layoutParent.showSnackBar(getString(R.string.message_general_error))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("TESTING", "onDestroyView")
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TESTING", "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("TESTING", "onDetach")
    }

    override fun onStart() {
        super.onStart()
        Log.d("TESTING", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TESTING", "onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TESTING", "onStop")
    }
}