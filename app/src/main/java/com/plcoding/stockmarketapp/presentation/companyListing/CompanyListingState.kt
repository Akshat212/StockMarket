package com.plcoding.stockmarketapp.presentation.companyListing

import com.plcoding.stockmarketapp.domain.model.CompanyListing

data class CompanyListingState(
    val listings: List<CompanyListing> = emptyList(),
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
    val searchQuery: String = ""
)
