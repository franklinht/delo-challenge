package com.frank.delochallenge.model.api

data class Response<out T>(val statusResponse: StatusResponse, val data: T?) {
    companion object {
        fun <T> success(data: T): Response<T> = Response(statusResponse = StatusResponse.SUCCESS, data = data)
        fun <T> error(data: T?): Response<T> = Response(statusResponse = StatusResponse.ERROR, data = data)
        fun <T> loading(data: T?): Response<T> = Response(statusResponse = StatusResponse.LOADING, data = data)
    }
}
