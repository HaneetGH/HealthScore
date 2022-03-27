package com.technorapper.root.data.repository


import android.content.Context
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.technorapper.root.constant.Task
import com.technorapper.root.data.WeatherApi
import com.technorapper.root.domain.DataState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class MainActivityRepository @Inject constructor(
    @ApplicationContext context: Context, private val weatherApi: WeatherApi
) : BaseRepository() {
    private val appContext = context.applicationContext
    var API_KEY = "fae7190d7e6433ec3a45285ffcf55c86"

    suspend fun fetchWeather(
        latLng: LatLng
    ): Flow<DataState> {
        return flow {
            emit(DataState.Loading(Task.FETCH_WEATHER))
            // var response: VehicleCategoriesList = null
            try {
                var response = weatherApi.getWeatherData(
                    latLng!!.latitude,
                    latLng!!.longitude,
                   " myPreference.getStoredUnit()",
                    API_KEY
                )
                emit(DataState.Success(response, Task.FETCH_WEATHER))
            } catch (e: Exception) {
                Log.e("fetch erroe",e.message.toString());
            }


        }.catch {
            emit(
                DataState.ErrorThrowable(
                    it,
                    Task.FETCH_WEATHER
                )
            )
        } // Use the IO thread for this Flow // Use the IO thread for this Flow // Use the IO thread for this Flow
    }


}

