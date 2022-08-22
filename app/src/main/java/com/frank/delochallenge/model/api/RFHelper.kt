package com.frank.delochallenge.model.api

class RFHelper(val api: Api) {
    suspend fun getPostalCodes() = api.getPostalCodes()
}