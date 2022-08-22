package com.frank.delochallenge.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.frank.delochallenge.R
import com.frank.delochallenge.databinding.FragmentSecondBinding
import com.frank.delochallenge.model.api.RFBuilder
import com.frank.delochallenge.model.api.RFHelper
import com.frank.delochallenge.model.api.StatusResponse
import com.frank.delochallenge.model.persistence.PostalCodeDatabase
import com.frank.delochallenge.ui.adapters.PostalCodeAdapter
import com.frank.delochallenge.viewmodel.PostalCodesViewModel
import com.frank.delochallenge.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SecondFragment : Fragment(R.layout.fragment_second) {

    private lateinit var viewModel: PostalCodesViewModel
    private val postalCodeDB by lazy { activity?.let { PostalCodeDatabase.getDatabase(it).postalCodeDAO() } }
    private lateinit var adapter: PostalCodeAdapter
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        fillScreen()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(RFHelper(RFBuilder.api))
        )[PostalCodesViewModel::class.java]
    }

    private fun fillScreen() {
        val cached = runBlocking  {
            postalCodeDB?.getCount()!! > 0
        }

        if(cached) {
            viewModel.getPostalCodes().observe(viewLifecycleOwner, Observer {
                it?.let { response ->
                    when (response.statusResponse) {
                        StatusResponse.SUCCESS -> {
                            lifecycleScope.launch {
                                viewModel.savePostalCode(response, postalCodeDB)
                            }
                            lifecycleScope.launch {
                                adapter = activity?.let { it1 -> PostalCodeAdapter(it1) }!!
                                fillPostalCodesList()
                            }
                            binding.rvPostalCode.visibility = View.VISIBLE
                            binding.badConnection.visibility = View.GONE
                            (activity as MainActivity).hideLoading()
                        }
                        StatusResponse.ERROR -> {
                            binding.rvPostalCode.visibility = View.GONE
                            binding.badConnection.visibility = View.VISIBLE
                            (activity as MainActivity).hideLoading()
                        }
                        StatusResponse.LOADING -> {
                            binding.rvPostalCode.visibility = View.GONE
                            binding.badConnection.visibility = View.GONE
                            (activity as MainActivity).showLoading()
                        }
                    }
                }
            })
        } else {
            fillPostalCodesList()
            (activity as MainActivity).hideLoading()
        }
    }

    //get info from db
    private fun fillPostalCodesList() {
        adapter.apply {
            notifyDataSetChanged()
        }
        binding.rvPostalCode.adapter = adapter
    }
}