package com.assessment.cleanarchitecture.domain.repository

import com.assessment.cleanarchitecture.data.model.Country
import com.assessment.cleanarchitecture.utils.ResponseHandler
import kotlinx.coroutines.flow.Flow

interface CountryRepository {
    suspend fun getCountries(): Flow<ResponseHandler<List<Country>>>
}