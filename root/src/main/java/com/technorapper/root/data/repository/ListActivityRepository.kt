package com.technorapper.root.data.repository


import android.content.Context
import android.util.Log
import com.technorapper.root.constant.Task
import com.technorapper.root.data.RootApi
import com.technorapper.root.data.room.database.dao.LocationDao
import com.technorapper.root.domain.DataState
import com.technorapper.root.proto.ProtoUserRepo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


class ListActivityRepository @Inject constructor(
    @ApplicationContext context: Context,
    private val locationDao: LocationDao,
    private val myPreference: ProtoUserRepo,
    private val rootApi: RootApi
) : BaseRepository() {
    private val appContext = context.applicationContext

    suspend fun isUserProfileThere(): Flow<DataState> {
        return flow {
            emit(DataState.Loading(Task.FETCH_ALL_LABS))
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
                        rootApi.getAllLabs(
                            tokenid, uid
                        )
                    }
                }



                result.collect {
                    emit(DataState.Success(it, Task.FETCH_ALL_LABS))
                }


            } catch (e: Exception) {

            }

            //firstHalfDeffered.await() + secondHalfDeffered.await()


        }.flowOn(Dispatchers.IO).catch {
            emit(
                DataState.ErrorThrowable(
                    it,
                    Task.FETCH_ALL_LABS
                )
            )
        } // Use the IO thread for this Flow // Use the IO thread for this Flow // Use the IO thread for this Flow
    }

    suspend fun nukeTable(
    ): Flow<DataState> {
        return flow {
            emit(DataState.Loading(Task.NUKE))
            // var response: VehicleCategoriesList = null
            try {
                var response = locationDao.nukeLocation()
                emit(DataState.Success(response, Task.NUKE))
            } catch (e: Exception) {
                Log.e("fetch erroe", e.message.toString());
            }


        }.flowOn(Dispatchers.IO).catch {
            emit(
                DataState.ErrorThrowable(
                    it,
                    Task.NUKE
                )
            )
        } // Use the IO thread for this Flow // Use the IO thread for this Flow // Use the IO thread for this Flow
    }


}

