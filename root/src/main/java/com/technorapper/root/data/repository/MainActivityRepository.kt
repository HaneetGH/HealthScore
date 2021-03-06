package com.technorapper.root.data.repository


import android.content.Context
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.technorapper.root.constant.Task
import com.technorapper.root.data.RootApi
import com.technorapper.root.domain.DataState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class MainActivityRepository @Inject constructor(
    @ApplicationContext context: Context, private val rootApi: RootApi
) : BaseRepository() {
    private val appContext = context.applicationContext
    var API_KEY = "fae7190d7e6433ec3a45285ffcf55c86"

    suspend fun fetchWeather(
        latLng: LatLng
    ): Flow<DataState> {
        return flow {
            emit(DataState.Loading(Task.FETCH_ALL_LABS))
            // var response: VehicleCategoriesList = null
            try {
                var response = rootApi.getAllLabs(
                    "latLng!!.latitude",
                    "latLng!!.longitude",

                )
                emit(DataState.Success(response, Task.FETCH_ALL_LABS))
            } catch (e: Exception) {
                Log.e("fetch erroe",e.message.toString());
            }


        }.catch {
            emit(
                DataState.ErrorThrowable(
                    it,
                    Task.FETCH_ALL_LABS
                )
            )
        } // Use the IO thread for this Flow // Use the IO thread for this Flow // Use the IO thread for this Flow
    }


}

