package com.assessment.cleanarchitecture.data.repository

import com.assessment.cleanarchitecture.data.model.Country
import com.assessment.cleanarchitecture.data.source.RemoteDataSource
import com.assessment.cleanarchitecture.domain.repository.CountryRepository
import com.assessment.cleanarchitecture.utils.ResponseHandler
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/*Repository impl - Decide the remote or local data source*/
class CountryRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    CountryRepository {
    override suspend fun getCountries(): Flow<ResponseHandler<List<Country>>> {
        return remoteDataSource.getCountries()
    }
}