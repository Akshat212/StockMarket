package com.plcoding.stockmarketapp.data.mappers

import com.plcoding.stockmarketapp.data.remote.dto.IntraDayInfoDto
import com.plcoding.stockmarketapp.domain.model.IntraDayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun IntraDayInfoDto.toIntraDayInfo(): IntraDayInfo {
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern)
    val localTime = LocalDateTime.parse(timestamp, formatter)
    return IntraDayInfo(
        date = localTime,
        close = close
    )
}