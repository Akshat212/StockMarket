package com.plcoding.stockmarketapp

import com.plcoding.stockmarketapp.data.remote.StockApiObject
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    private val stockApi = StockApiObject

    @Test
    fun `Get listings`() {
//        val listings = stockApi.api.getCompanyListings().execute()
//        assertNotNull(listings.body())
//        println("Listings ${listings.body()?.string()}")
    }
}