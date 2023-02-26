package com.plcoding.stockmarketapp.presentation.companyListing

sealed class CompanyListingEvents {
    object Refresh : CompanyListingEvents()
    data class SearchQueryChanged(val query: String) : CompanyListingEvents()
}
