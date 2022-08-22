package com.frank.delochallenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.frank.delochallenge.model.api.Response
import com.frank.delochallenge.model.data.Repository
import com.frank.delochallenge.model.persistence.PostalCode
import com.frank.delochallenge.model.persistence.PostalCodeDAO
import kotlinx.coroutines.Dispatchers
import okhttp3.ResponseBody
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVRecord

import org.apache.commons.csv.CSVParser
import java.io.FileReader
import java.io.IOException


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

    fun savePostalCode(data: Response<ResponseBody>, postalCodeDB: PostalCodeDAO?) {
        var skipped = false
//        val csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase()
//
//        try {
//            FileReader(data.data?.string()).use { fileReader ->
//                CSVParser(fileReader, csvFormat).use { csvParser ->
//                    for (csvRecord in csvParser) {
//                        if(skipped) {
//                            val address = csvRecord["nome_localidade"]
//                            val postalCodeNumber = csvRecord["num_cod_postal"]
//                            val postalCodeExt = csvRecord["ext_cod_postal"]
//                            println("$address,$postalCodeNumber,$postalCodeExt")
//                            postalCodeDB?.insertPostalCodes(PostalCode(0, address, postalCodeNumber.plus("-").plus(postalCodeExt)))
//                        }
//                        skipped = true
//                    }
//                }
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }

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