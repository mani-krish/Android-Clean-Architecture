package com.assessment.cleanarchitecture.data.service

import com.assessment.cleanarchitecture.data.model.Country
import retrofit2.Response
import retrofit2.http.GET

/*Web Service*/
interface WebService {
    @GET("db25946fd77c5873b0303b858e861ce724e0dcd0/countries.json")
    suspend fun getCountries(): Response<List<Country>>
}