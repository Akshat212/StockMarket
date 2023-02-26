package com.plcoding.stockmarketapp.di

import com.plcoding.stockmarketapp.data.csv.CSVParser
import com.plcoding.stockmarketapp.data.csv.CompanyListingParser
import com.plcoding.stockmarketapp.data.csv.IntraDayInfoParser
import com.plcoding.stockmarketapp.data.repo.StockRepositoryImpl
import com.plcoding.stockmarketapp.domain.model.CompanyListing
import com.plcoding.stockmarketapp.domain.model.IntraDayInfo
import com.plcoding.stockmarketapp.domain.repo.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsCompanyListingParser(
        companyListingParser: CompanyListingParser
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindsIntraDayInfoParser(
        intraDayInfoParser: IntraDayInfoParser
    ): CSVParser<IntraDayInfo>

    @Binds
    @Singleton
    abstract fun bindsStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository
}