package com.plcoding.stockmarketapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CompanyListingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyListings(
        listings: List<CompanyListingEntity>
    )

    @Query(
        "DELETE FROM CompanyListingEntity"
    )
    suspend fun clearCompanyListings()

    @Query(
        """
            Select *
            From CompanyListingEntity
            Where LOWER(name) Like '%' || LOWER(:query) || '%' Or
                UPPER(:query) == symbol 
        """
    )
    suspend fun searchCompany(
        query: String
    ): List<CompanyListingEntity>
}