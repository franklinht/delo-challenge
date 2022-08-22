package com.frank.delochallenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.frank.delochallenge.model.api.RFHelper
import com.frank.delochallenge.model.data.Repository


class ViewModelFactory(private val rfHelper: RFHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostalCodesViewModel::class.java)) {
            return PostalCodesViewModel(Repository(rfHelper)) as T
        }
        throw RuntimeException()
    }
}