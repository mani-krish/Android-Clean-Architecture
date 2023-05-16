package com.assessment.cleanarchitecture.data.source

import com.assessment.cleanarchitecture.data.model.Country
import com.assessment.cleanarchitecture.data.service.WebService
import com.assessment.cleanarchitecture.utils.ResponseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/*Call the remote source - API - Countries*/
class RemoteDataSource @Inject constructor(private val api: WebService) {

    fun getCountries(): Flow<ResponseHandler<List<Country>>> =
        flow {
            try {
                emit(ResponseHandler.Loading())
                val response = api.getCountries()
                if (response.isSuccessful) {
                    emit(ResponseHandler.Success(response.body()))
                } else {
                    emit(ResponseHandler.Error(response.message()))
                }
            } catch (e: Exception) {
                emit(ResponseHandler.Error(e.message.toString()))
            }
        }.flowOn((Dispatchers.IO))
}