package com.plcoding.stockmarketapp.data.remote

import com.plcoding.stockmarketapp.data.remote.dto.CompanyInfoDto
import com.plcoding.stockmarketapp.util.ApiUtils.API_KEY
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {

    @GET("query?")
    suspend fun getCompanyListings(
        @Query("function") function: String = "LISTING_STATUS",
        @Query("apikey") apikey: String = API_KEY,
    ): ResponseBody

    @GET("query?")
    suspend fun getIntraDayInfo(
        @Query("function") function: String = "TIME_SERIES_INTRADAY",
        @Query("interval") interval: String = "60min",
        @Query("datatype") datatype: String = "csv",
        @Query("symbol") symbol: String,
        @Query("apikey") apikey: String = API_KEY
    ): ResponseBody

    @GET("query?")
    suspend fun getCompanyInfo(
        @Query("function") function: String = "OVERVIEW",
        @Query("symbol") symbol: String,
        @Query("apikey") apikey: String = API_KEY
    ): CompanyInfoDto
}