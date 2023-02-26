package com.plcoding.stockmarketapp.presentation.companyInfo

import com.plcoding.stockmarketapp.domain.model.CompanyInfo
import com.plcoding.stockmarketapp.domain.model.IntraDayInfo

data class CompanyInfoState(
    val stockInfo: List<IntraDayInfo> = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)