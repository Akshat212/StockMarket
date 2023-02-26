package com.plcoding.stockmarketapp.data.repo

import com.plcoding.stockmarketapp.data.csv.CSVParser
import com.plcoding.stockmarketapp.data.local.StockDatabase
import com.plcoding.stockmarketapp.data.mappers.fromCompanyListingEntity
import com.plcoding.stockmarketapp.data.mappers.toCompanyInfo
import com.plcoding.stockmarketapp.data.mappers.toCompanyListingEntity
import com.plcoding.stockmarketapp.data.remote.StockApi
import com.plcoding.stockmarketapp.domain.model.CompanyInfo
import com.plcoding.stockmarketapp.domain.model.CompanyListing
import com.plcoding.stockmarketapp.domain.model.IntraDayInfo
import com.plcoding.stockmarketapp.domain.repo.StockRepository
import com.plcoding.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val database: StockDatabase,
    private val csvParser: CSVParser<CompanyListing>,
    private val intraDayInfoParser: CSVParser<IntraDayInfo>
) : StockRepository {

    private val dao = database.dao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val companyListing = dao.searchCompany(query)
            emit(
                Resource.Success(
                    companyListing.map {
                        it.fromCompanyListingEntity()
                    }
                )
            )

            val isLocalCacheEmpty = companyListing.isEmpty() && query.isBlank()
            val shouldWeJustLoadFromCache = !isLocalCacheEmpty && !fetchFromRemote

            if (shouldWeJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteListings = try {
                val response = api.getCompanyListings()
                csvParser.parseCSVData(response.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Network error"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(e.message()))
                null
            }

            remoteListings?.let { listings ->
                dao.clearCompanyListings()
                dao.insertCompanyListings(listings.map { it.toCompanyListingEntity() })

                emit(Resource.Success(dao.searchCompany("").map { it.fromCompanyListingEntity() }))
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getIntraDayInfo(symbol: String): Resource<List<IntraDayInfo>> {
        return try {
            val response = api.getIntraDayInfo(symbol = symbol)
            val results = intraDayInfoParser.parseCSVData(response.byteStream())
            Resource.Success(results)
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(
                errorMsg = "Couldn't load IntraDay info"
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(
                errorMsg = "Couldn't load IntraDay info"
            )
        }
    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try {
            val response = api.getCompanyInfo(symbol = symbol)
            Resource.Success(response.toCompanyInfo())
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(
                errorMsg = "Couldn't load IntraDay info"
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(
                errorMsg = "Couldn't load IntraDay info"
            )
        }
    }
}