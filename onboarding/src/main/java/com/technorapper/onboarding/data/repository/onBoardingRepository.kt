package com.technorapper.onboarding.data.repository


import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.technorapper.onboarding.constant.Task
import com.technorapper.onboarding.data.NetworkLayer
import com.technorapper.onboarding.domain.DataState
import com.technorapper.root.proto.ProtoUserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking


class onBoardingRepository : BaseRepository() {

    lateinit var myPreference: ProtoUserRepo
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

            try {
                var uid = ""
                var tokenid = ""
                val result = runBlocking {
                    myPreference.getUserID().flatMapConcat { id ->
                        uid = id
                        myPreference.getTokenID()
                    }.map { token ->
                        tokenid = token
                    }.map {
                        NetworkLayer.create().addusers(
                            email,
                            profession,
                            userDOB,
                            uid,
                            userLastLocation,
                            userName,
                            tokenid,
                            uid
                        )
                    }
                }
                result.collect {
                    emit(DataState.Success(it, Task.UPDATE_ONBOARD))
                }


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

    fun isUserProfileThere(): Flow<DataState> {
        return flow {
            emit(DataState.Loading(Task.IS_PROFILE_THERE))
            // var response: VehicleCategoriesList = null

            try {
                var uid = ""
                var tokenid = ""
                val result = runBlocking {
                    myPreference.getUserID().flatMapMerge { id ->
                        uid = id
                        myPreference.getTokenID()
                    }.map { token ->
                        tokenid = token
                    }.map {
                        NetworkLayer.create().isProfileThere(
                            uid,
                            tokenid
                        )
                    }
                }



                result.collect {
                    emit(DataState.Success(it, Task.IS_PROFILE_THERE))
                }


            } catch (e: Exception) {

            }

            //firstHalfDeffered.await() + secondHalfDeffered.await()


        }.flowOn(Dispatchers.IO).catch {
            emit(
                DataState.ErrorThrowable(
                    it,
                    Task.IS_PROFILE_THERE
                )
            )
        } // Use the IO thread for this Flow // Use the IO thread for this Flow // Use the IO thread for this Flow
    }

    fun pushPrefence(myPreference: ProtoUserRepo) {
        this.myPreference = myPreference
    }


}

