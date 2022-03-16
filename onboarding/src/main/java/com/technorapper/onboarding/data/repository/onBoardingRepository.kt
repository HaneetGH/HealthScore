package com.technorapper.onboarding.data.repository


import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.technorapper.onboarding.constant.Task
import com.technorapper.onboarding.domain.DataState
import com.technorapper.root.data.MyPreference
import com.technorapper.root.data.room.database.dao.LocationDao
import dagger.hilt.android.qualifiers.ApplicationContext

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class onBoardingRepository  : BaseRepository() {

lateinit var myPreference: MyPreference
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

    fun updateUserInfo(
        userDOB: String,
        userLastLocation: String,
        userName: String,
        email: String,
        profession: String
    ): Flow<DataState> {
        return flow {
            emit(DataState.Loading(Task.ONBOARD))
            // var response: VehicleCategoriesList = null
            val db = Firebase.firestore
            try {
                val user = hashMapOf(
                    "userDOB" to userDOB,
                    "userID" to myPreference.getStoredUnit(),
                    "userLastLocation" to userLastLocation,
                    "userName" to userName,
                    "email" to email,
                    "profession" to profession
                )

// Add a new document with a generated ID
              val result =  db.collection("users").add(user)

                emit(DataState.Success(result, Task.UPDATE_ONBOARD))
            } catch (e: Exception) {
                Log.e("fetch erroe", e.message.toString());
            }


        }.flowOn(Dispatchers.IO).catch {
            emit(
                DataState.ErrorThrowable(
                    it,
                    Task.UPDATE_ONBOARD
                )
            )
        } // Use the IO thread for this Flow // Use the IO thread for this Flow // Use the IO thread for this Flow
    }

    fun pushPrefence(myPreference: MyPreference) {
        this.myPreference =myPreference
    }


}
