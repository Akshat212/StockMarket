package com.plcoding.stockmarketapp.data.remote

import com.plcoding.stockmarketapp.util.ApiUtils
import retrofit2.Retrofit

object StockApiObject {
    val api : StockApi by lazy {
        val client = Retrofit.Builder()
            .baseUrl(ApiUtils.BASE_URL)
            .build()

        client.create(StockApi::class.java)
    }
}