package com.technorapper.root.ui.list

import android.content.ContentValues
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.technorapper.root.data.data_model.LocationTable

import com.technorapper.root.data.repository.ListActivityRepository
import com.technorapper.root.domain.DataState
import com.technorapper.root.proto.ProtoUserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import javax.inject.Inject

@HiltViewModel
class ListActivityViewModel @Inject constructor(
    private val repository: ListActivityRepository,
    private val userRepo: ProtoUserRepo

) : ViewModel() {
    var reset = ObservableBoolean()
    private val _uiState: MutableLiveData<DataState> = MutableLiveData()
    val uiState: MutableLiveData<DataState> get() = _uiState
    fun setStateEvent(mainStateEvent: MainListStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {

                is MainListStateEvent.FetchBookmark -> {
                    repository.fetchBookmark(
                    ).collect { uiState.value = it }
                }

                is MainListStateEvent.DeleteItem -> {
                    repository.deleteItem(
                        mainStateEvent.locationTable
                    ).collect { uiState.value = it }
                }
                is MainListStateEvent.Reset -> {
                    repository.nukeTable(
                    ).collect { uiState.value = it }
                }

            }

        }
    }

    suspend fun getUser() {
        val db = Firebase.firestore
        var uid = ""
        userRepo.getUserID().collect { uid = it }
        db.collection("users")
            .whereEqualTo("userID", uid)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                Log.d(
                    ContentValues.TAG,
                    "Current users : ${snapshots!!.documents[0].data?.get("userName")}"
                )
            }
    }

    fun showFlow() {
        viewModelScope.launch {
            userRepo?.getUserID()?.collect { state ->
                withContext(Dispatchers.Main) {
                    Log.d("Value Root", state)
                }
            }
        }
    }


}

sealed class MainListStateEvent {

    object FetchBookmark : MainListStateEvent()
    data class DeleteItem(val locationTable: LocationTable) : MainListStateEvent()
    object None : MainListStateEvent()
    object Reset : MainListStateEvent()
    data class UpdateUnit(val which: Boolean) : MainListStateEvent()
    object FetchDefault : MainListStateEvent()
}


