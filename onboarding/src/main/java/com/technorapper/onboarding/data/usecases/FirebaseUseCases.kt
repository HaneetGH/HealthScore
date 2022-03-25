package com.technorapper.onboarding.data.usecases

import com.technorapper.onboarding.data.repository.onBoardingRepository
import com.technorapper.onboarding.domain.DataState
import com.technorapper.root.data.MyPreference

import kotlinx.coroutines.flow.Flow


class FirebaseUseCases constructor(
    private val preference: MyPreference,
    private val repository: onBoardingRepository

) {

    init {
        repository.pushPrefence(preference)

    }

    suspend fun registerUserWithPhoneNumber(verID: String, otp: String): Flow<DataState> {
        return repository.registerUser(verID, otp)
    }

    suspend fun updateUserInfo(
        userDOB: String,
        userLastLocation: String,
        userName: String,
        email: String,
        profession: String
    ): Flow<DataState> {
        return repository.updateUserInfo(userDOB, userLastLocation, userName, email, profession);
    }
    suspend fun isUserProfileThere(

    ): Flow<DataState> {
        return repository.isUserProfileThere();
    }

}