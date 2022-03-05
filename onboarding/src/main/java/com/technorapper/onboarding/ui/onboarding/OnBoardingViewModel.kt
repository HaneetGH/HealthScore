package com.technorapper.onboarding.ui.onboarding

import android.content.ContentValues.TAG
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.technorapper.onboarding.data.data_model.LocationTable
import com.technorapper.onboarding.data.repository.ListActivityRepository
import com.technorapper.onboarding.data.usecases.FirebaseUseCases
import com.technorapper.onboarding.domain.DataState

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class OnBoardingViewModel : ViewModel() {
    private val useCases: FirebaseUseCases = FirebaseUseCases()
    var reset = ObservableBoolean()
    private val _uiState: MutableLiveData<DataState> = MutableLiveData()
    val uiState: MutableLiveData<DataState> get() = _uiState
    fun setStateEvent(mainStateEvent: MainListStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {


                is MainListStateEvent.RegisterUser -> {
                    useCases.registerUserWithPhoneNumber(mainStateEvent.verID, mainStateEvent.otp)
                        .collect {
                            Log.d("return", it.toString())
                            uiState.value = it
                        }

                }
            }

        }
    }


}


sealed class MainListStateEvent {

    object FetchBookmark : MainListStateEvent()
    data class DeleteItem(val locationTable: LocationTable) : MainListStateEvent()
    object None : MainListStateEvent()
    object Reset : MainListStateEvent()
    data class UpdateUnit(val which: Boolean) : MainListStateEvent()
    data class RegisterUser(val otp: String, val verID: String) : MainListStateEvent()
}


