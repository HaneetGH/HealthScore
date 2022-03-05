package com.technorapper.root.ui.list

import android.content.ContentValues.TAG
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import com.technorapper.root.R
import com.technorapper.root.base.BaseClass
import com.technorapper.root.databinding.RootActivityBinding



class RootActivity : BaseClass() {


    private val viewModel by viewModels<ListActivityViewModel>()
    lateinit var binding: RootActivityBinding


    override fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.root_activity)
        binding.bottomNavigation.setupWithNavController(Navigation.findNavController(this, R.id.fragmentContainerView))
        val db = Firebase.firestore
        db.collection("users")
            .whereEqualTo("userID", 1)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                Log.d(TAG, "Current users : ${snapshots!!.documents[0].data?.get("userName")}")
            }
    }


    override fun attachViewModel() {
        //viewModel.setStateEvent(MainListStateEvent.FetchBookmark)
        //    viewModel.uiState.observe(this, Observer { parse(it) })
    }


}


