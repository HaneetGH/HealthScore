package com.technorapper.onboarding.data.repository


import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.technorapper.onboarding.constant.Task
import com.technorapper.onboarding.domain.DataState

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class ListActivityRepository : BaseRepository() {


    suspend fun registerUser(
        storedVerificationId: String, code: String
    ): Flow<DataState> {
        return flow {
            emit(DataState.Loading(Task.ONBOARD))
            // var response: VehicleCategoriesList = null
            val credential = PhoneAuthProvider.getCredential(storedVerificationId!!, code)
            try {
                var result = FirebaseAuth.getInstance()!!.signInWithCredential(credential)
                emit(DataState.Success(result, Task.ONBOARD))


            } catch (e: Exception) {
                Log.e("fetch erroe", e.message.toString());
            }


        }.flowOn(Dispatchers.IO).catch {
            emit(
                DataState.ErrorThrowable(
                    it,
                    Task.ONBOARD
                )
            )
        } // Use the IO thread for this Flow // Use the IO thread for this Flow // Use the IO thread for this Flow
    }


}

