package com.assessment.cleanarchitecture.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assessment.cleanarchitecture.R
import com.assessment.cleanarchitecture.data.model.Country
import com.assessment.cleanarchitecture.domain.usecase.GetCountryUseCase
import com.assessment.cleanarchitecture.utils.ResourceProvider
import com.assessment.cleanarchitecture.utils.ResponseHandler
import com.assessment.cleanarchitecture.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ListCountryViewModel @Inject constructor(
    private val useCase: GetCountryUseCase, private val resourceProvider: ResourceProvider
) : ViewModel() {

    // Backing property to avoid state updates from other classes
    private val _uiState: MutableStateFlow<ResponseHandler<List<Country>>> =
        MutableStateFlow(ResponseHandler.Loading())

    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<ResponseHandler<List<Country>>> = _uiState

    init {
        fetchCountries()
        Log.d("TESTING", "init")
    }

    /*Load the countries*/
    private fun fetchCountries() {
        viewModelScope.launch {
            try {
                useCase.execute().onStart {
                    _uiState.value = ResponseHandler.Loading()
                }.catch {
                    _uiState.value = ResponseHandler.Error(it.message.toString())
                }.collect {
                    when (it.status) {
                        Status.SUCCESS -> {
                            _uiState.value = ResponseHandler.Success(it.data)
                        }

                        Status.LOADING -> {
                            _uiState.value = ResponseHandler.Loading()
                        }

                        else -> {
                            _uiState.value =
                                ResponseHandler.Error(resourceProvider.getString(R.string.message_no_data))
                        }
                    }
                }
            } catch (e: HttpException) {
                _uiState.value = ResponseHandler.Error(
                    e.message ?: resourceProvider.getString(R.string.message_general_error)
                )
            } catch (e: IOException) {
                _uiState.value =
                    ResponseHandler.Error(resourceProvider.getString(R.string.message_internet_error))
            } catch (e: Exception) {
                _uiState.value =
                    ResponseHandler.Error(resourceProvider.getString(R.string.message_general_error))
            }
        }
    }

    override fun onCleared() {
        Log.d("TESTING", "onCleared")
    }

}
