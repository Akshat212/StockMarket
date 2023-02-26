package com.plcoding.stockmarketapp.util

sealed class Resource<T>(val data: T? = null, val errorMsg: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(errorMsg: String, data: T? = null) : Resource<T>(data, errorMsg)
    class Loading<T>(isLoading: Boolean) : Resource<T>()
}
