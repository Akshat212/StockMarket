package com.plcoding.stockmarketapp.data.csv

import java.io.InputStream

interface CSVParser<T> {
    suspend fun parseCSVData(stream: InputStream): List<T>
}