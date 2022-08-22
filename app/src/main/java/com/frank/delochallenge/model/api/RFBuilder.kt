package com.frank.delochallenge.model.api

import com.frank.delochallenge.BuildConfig
import retrofit2.Retrofit

object RFBuilder {
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
//            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    val api: Api = getRetrofit().create(Api::class.java)
}