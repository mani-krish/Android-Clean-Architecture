package com.assessment.cleanarchitecture.domain.usecase

import com.assessment.cleanarchitecture.data.model.Country
import com.assessment.cleanarchitecture.domain.repository.CountryRepository
import com.assessment.cleanarchitecture.utils.ResponseHandler
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/*Use-case driven architecture - invoke the repository interface*/
class GetCountryUseCase @Inject constructor(private val repository: CountryRepository) {

    suspend fun execute(): Flow<ResponseHandler<List<Country>>> {
        return repository.getCountries()
    }

}