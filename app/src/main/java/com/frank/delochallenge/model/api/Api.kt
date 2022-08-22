package com.frank.delochallenge.model.api

import okhttp3.ResponseBody
import retrofit2.http.GET

interface Api {
    @GET("codigos_postais.csv")
    suspend fun getPostalCodes(): ResponseBody
}