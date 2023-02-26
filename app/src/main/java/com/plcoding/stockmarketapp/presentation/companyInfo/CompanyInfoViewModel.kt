package com.plcoding.stockmarketapp.presentation.companyInfo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.stockmarketapp.domain.repo.StockRepository
import com.plcoding.stockmarketapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val stockRepository: StockRepository
) : ViewModel() {

    var state by mutableStateOf(CompanyInfoState())

    init {
        viewModelScope.launch {
            val symbol = savedStateHandle.get<String>("symbol") ?: return@launch
            val companyInfo = async { stockRepository.getCompanyInfo(symbol) }
            val intraDayInfo = async { stockRepository.getIntraDayInfo(symbol) }

            when (val result = companyInfo.await()) {
                is Resource.Error -> {
                    state = state.copy(
                        company = null,
                        isLoading = false,
                        error = result.errorMsg
                    )
                }
                is Resource.Loading -> {}
                is Resource.Success -> {
                    state = state.copy(
                        company = result.data,
                        isLoading = false,
                        error = null
                    )
                }
            }

            when (val result = intraDayInfo.await()) {
                is Resource.Error -> {
                    state = state.copy(
                        company = null,
                        isLoading = false,
                        error = result.errorMsg
                    )
                }
                is Resource.Loading -> {}
                is Resource.Success -> {
                    state = state.copy(
                        stockInfo = result.data ?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                }
            }
        }
    }
}