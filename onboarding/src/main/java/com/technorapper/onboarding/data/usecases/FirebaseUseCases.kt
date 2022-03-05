package com.technorapper.onboarding.data.usecases

import android.content.Context
import com.technorapper.onboarding.data.MyPreference
import com.technorapper.onboarding.data.repository.ListActivityRepository
import com.technorapper.onboarding.data.room.database.dao.LocationDao
import com.technorapper.onboarding.domain.DataState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FirebaseUseCases @Inject constructor(
    @ApplicationContext context: Context,
    private val repository: ListActivityRepository

) {
    suspend fun registerUserWithPhoneNumber(verID: String, otp: String): Flow<DataState> {
        return repository.registerUser(verID, otp)
    }

}