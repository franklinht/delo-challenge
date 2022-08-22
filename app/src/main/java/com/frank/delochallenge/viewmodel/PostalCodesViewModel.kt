package com.frank.delochallenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.frank.delochallenge.model.api.Response
import com.frank.delochallenge.model.data.Repository
import com.frank.delochallenge.model.persistence.PostalCode
import com.frank.delochallenge.model.persistence.PostalCodeDAO
import kotlinx.coroutines.Dispatchers
import okhttp3.ResponseBody

class PostalCodesViewModel (private val repository: Repository) : ViewModel() {

    fun getPostalCodes() = liveData(Dispatchers.IO) {
        emit(Response.loading(data = null))

        try {
            emit(Response.success(data = repository.getPostalCodes()))
        } catch (exception: Exception) {
            exception.printStackTrace()
            emit(Response.error(data = null))
        }
    }

    suspend fun savePostalCode(data: Response<ResponseBody>, postalCodeDB: PostalCodeDAO?) {
        var skipped = false
        for (postalCode in data.data?.string()?.lines()!!) {
            //skip first position
            if(skipped) {
                postalCodeDB?.insertPostalCodes(
                    PostalCode(0, postalCode.split(",")[3],
                        postalCode.split(",")[14]
                            .plus("-")
                            .plus(postalCode.split(",")[15]))
                )
            }
            skipped = true
        }
    }
}