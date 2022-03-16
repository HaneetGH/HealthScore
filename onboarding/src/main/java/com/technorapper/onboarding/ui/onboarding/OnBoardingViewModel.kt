package com.technorapper.onboarding.ui.onboarding

import android.app.Activity
import android.app.DatePickerDialog
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technorapper.onboarding.R
import com.technorapper.onboarding.data.repository.onBoardingRepository
import com.technorapper.onboarding.data.usecases.FirebaseUseCases
import com.technorapper.onboarding.domain.DataState
import com.technorapper.root.data.MyPreference

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*


class OnBoardingViewModel : ViewModel() {

    var reset = ObservableBoolean()
    private val _uiState: MutableLiveData<DataState> = MutableLiveData()
    val uiState: MutableLiveData<DataState> get() = _uiState
    lateinit var myPreference: MyPreference

    lateinit var useCases: FirebaseUseCases
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
                is MainListStateEvent.UpdateUserInfo -> {
                    useCases.updateUserInfo(
                        mainStateEvent.userDOB,
                        mainStateEvent.userLastLocation,
                        mainStateEvent.userName,
                        mainStateEvent.email,
                        mainStateEvent.professiona
                    )
                        .collect {
                            Log.d("return", it.toString())
                            uiState.value = it
                        }

                }
            }

        }
    }

    fun saveInPref(uid: String?, activity: Activity) {
        if (uid != null) {
            myPreference = MyPreference(activity)
            myPreference.setStoredUnit(uid)
        }

    }

    fun pushContext(requireActivity: Activity) {
        myPreference = MyPreference(requireActivity)
        useCases = FirebaseUseCases(myPreference, onBoardingRepository());
    }

    fun fetchTimeAndDate(activity: FragmentActivity?): MutableLiveData<String> {
        var timeLiveData: MutableLiveData<String> = MutableLiveData();
        val mYear: Int
        val mMonth: Int
        val mDay: Int
        val c = Calendar.getInstance()
        mYear = c[Calendar.YEAR]
        mMonth = c[Calendar.MONTH]
        mDay = c[Calendar.DAY_OF_MONTH]
        val datePickerDialog = DatePickerDialog(
            activity!!,
            R.style.DialogTheme,
            { _, year, month, day ->
                timeLiveData.value = "$day-$month-$year"

            },
            mYear,
            mMonth,
            mDay
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()

        return timeLiveData;
    }
}


sealed class MainListStateEvent {

    object FetchBookmark : MainListStateEvent()
    data class UpdateUserInfo(
        val userDOB: String,
        val userLastLocation: String,
        val userName: String,
        val email: String,
        val professiona: String
    ) : MainListStateEvent()

    data class RegisterUser(val otp: String, val verID: String) : MainListStateEvent()
}


