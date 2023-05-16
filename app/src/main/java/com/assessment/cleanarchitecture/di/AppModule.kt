package com.assessment.cleanarchitecture.di

import com.assessment.cleanarchitecture.data.repository.CountryRepositoryImpl
import com.assessment.cleanarchitecture.data.service.WebService
import com.assessment.cleanarchitecture.data.source.RemoteDataSource
import com.assessment.cleanarchitecture.domain.repository.CountryRepository
import com.assessment.cleanarchitecture.utils.Constants
import com.assessment.cleanarchitecture.utils.LoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideHTTPLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun provideCustomInterceptor() = LoggingInterceptor()

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: LoggingInterceptor) =
        OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            .addNetworkInterceptor(loggingInterceptor).build()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder().addConverterFactory(gsonConverterFactory)
            .baseUrl(Constants.BASE_URL).client(okHttpClient).build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): WebService =
        retrofit.create(WebService::class.java)

    @Provides
    @Singleton
    fun provideDataSource(countryApiService: WebService) = RemoteDataSource(countryApiService)

    @Provides
    @Singleton
    fun provideRepository(remoteDataSource: RemoteDataSource): CountryRepository =
        CountryRepositoryImpl(remoteDataSource)
}