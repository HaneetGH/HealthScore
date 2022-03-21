package com.technorapper.root.ui.list

import android.content.ContentValues
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.technorapper.root.data.MyPreference
import com.technorapper.root.data.data_model.LocationTable

import com.technorapper.root.data.repository.ListActivityRepository
import com.technorapper.root.domain.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class ListActivityViewModel @Inject constructor(
    private val repository: ListActivityRepository,private val myPreference: MyPreference

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
                is MainListStateEvent.UpdateUnit -> {
                    repository.updateUnit(
                        mainStateEvent.which
                    )

                }
                is MainListStateEvent.FetchDefault -> {
                    repository.fetchDefault(
                    ).collect { uiState.value = it }

                }
            }

        }
    }

    fun getUser() {
        val db = Firebase.firestore
        db.collection("users")
            .whereEqualTo("userID", myPreference.getStoredUnit())
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                Log.d(ContentValues.TAG, "Current users : ${snapshots!!.documents[0].data?.get("userName")}")
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


