package com.frank.delochallenge.model.data

import com.frank.delochallenge.model.api.RFHelper

class Repository(private val rfHelper: RFHelper) {
    suspend fun getPostalCodes() = rfHelper.getPostalCodes()
}