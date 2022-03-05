package com.technorapper.onboarding.data.usecases

import com.technorapper.onboarding.data.repository.ListActivityRepository
import com.technorapper.onboarding.domain.DataState

import kotlinx.coroutines.flow.Flow


class FirebaseUseCases  constructor(

    private val repository: ListActivityRepository = ListActivityRepository()

) {
    suspend fun registerUserWithPhoneNumber(verID: String, otp: String): Flow<DataState> {
        return repository.registerUser(verID, otp)
    }

}