package com.plcoding.stockmarketapp.presentation.companyListing

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.stockmarketapp.domain.repo.StockRepository
import com.plcoding.stockmarketapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListingsViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {

    var state by mutableStateOf(CompanyListingState())
    var searchJob: Job? = null

    init {
        getCompanyListings()
    }

    fun onEvent(events: CompanyListingEvents) {
        when (events) {
            CompanyListingEvents.Refresh -> {
                getCompanyListings(fetchFromRemote = true)
            }
            is CompanyListingEvents.SearchQueryChanged -> {
                state = state.copy(searchQuery = events.query)
                searchJob?.cancel()

                searchJob = viewModelScope.launch {
                    delay(500L)
                    getCompanyListings()
                }
            }
        }
    }

    private fun getCompanyListings(
        query: String = state.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch {
            repository
                .getCompanyListings(fetchFromRemote, query)
                .collect { resource ->
                    when (resource) {
                        is Resource.Error -> Unit
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = true
                            )
                        }
                        is Resource.Success -> {
                            resource.data?.let { companies ->
                                state = state.copy(
                                    listings = companies
                                )
                            }
                        }
                    }
                }
        }
    }
}