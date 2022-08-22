package com.frank.delochallenge.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.frank.delochallenge.R
import com.frank.delochallenge.databinding.FragmentPostalCodesBinding
import com.frank.delochallenge.model.api.RFBuilder
import com.frank.delochallenge.model.api.RFHelper
import com.frank.delochallenge.model.api.StatusResponse
import com.frank.delochallenge.model.persistence.PostalCode
import com.frank.delochallenge.model.persistence.PostalCodeDatabase
import com.frank.delochallenge.ui.adapters.PostalCodeAdapter
import com.frank.delochallenge.viewmodel.PostalCodesViewModel
import com.frank.delochallenge.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class PostalCodesFragment : Fragment(R.layout.fragment_postal_codes) {

    private lateinit var viewModel: PostalCodesViewModel
    private val postalCodeDB by lazy { activity?.let { PostalCodeDatabase.getDatabase(it).postalCodeDAO() } }
    private lateinit var adapter: PostalCodeAdapter
    private var _binding: FragmentPostalCodesBinding? = null
    private val binding get() = _binding!!
    private lateinit var postalCodesList : List<PostalCode>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostalCodesBinding.inflate(inflater, container, false)
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

        if(!cached) {
            viewModel.getPostalCodes().observe(viewLifecycleOwner, Observer {
                it?.let { response ->
                    when (response.statusResponse) {
                        StatusResponse.SUCCESS -> {
                            lifecycleScope.launch {
                                viewModel.savePostalCode(response, postalCodeDB)
                            }
                            runBlocking {
                                postalCodesList = postalCodeDB?.getAllPostalCodes()!!
                            }
                            lifecycleScope.launch {
                                fillPostalCodesList(postalCodesList)
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
            runBlocking {
                postalCodesList = postalCodeDB?.getAllPostalCodes()!!
            }

            fillPostalCodesList(postalCodesList)
            (activity as MainActivity).hideLoading()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fillPostalCodesList(postalCodesList: List<PostalCode>?) {
        adapter = PostalCodeAdapter(arrayListOf())
        adapter.apply {
            if (postalCodesList != null) {
                addPostalCodes(postalCodesList)
            }
            notifyDataSetChanged()
        }
        binding.rvPostalCode.adapter = adapter
    }
}