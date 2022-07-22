package com.technorapper.root.ui.list.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.technorapper.root.base.BaseFragment
import com.technorapper.root.constant.Task
import com.technorapper.root.data.data_model.lablist.Lab
import com.technorapper.root.data.data_model.lablist.LabsListModel
import com.technorapper.root.domain.DataState
import com.technorapper.root.ui.compose.RootCompose
import com.technorapper.root.ui.list.ListActivityViewModel
import com.technorapper.root.ui.list.MainListStateEvent
import com.technorapper.root.ui.location.LocationFetchFromMapActivity
import com.technorapper.root.ui.theme.AppTheme

class ListFragment : BaseFragment() {


    private val viewModel by viewModels<ListActivityViewModel>()
    private val listOfLabs: ArrayList<Lab> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setEvents();
        return ComposeView(requireContext()).apply {
            setContent {
                val scaffoldState = rememberScaffoldState()
                 AppTheme(
                    displayProgressBar = false,
                    scaffoldState = scaffoldState,
                    darkTheme = false,
                ) {
                    Scaffold(
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        }
                    ) {
                        RootCompose(viewModel)
                    }
                }
            }
        }
    }


    private fun setEvents() {

    }


    override fun attachViewModel() {
        viewModel.setStateEvent(MainListStateEvent.FetchAllLabs)
        viewModel.uiState.observe(this, Observer { parse(it) })
    }

    override fun onResume() {
        super.onResume()
        viewModel?.setStateEvent(MainListStateEvent.FetchAllLabs)
    }

    private fun parse(it: DataState?) {

        if (it != null) {
            when (it) {
                is DataState.Success<*> -> {
                    Log.d("Api Response", "SUCCES");

                    if (it?.data != null) {
                        when (it.task) {
                            Task.FETCH_ALL_LABS -> {
                                try {
                                    val value = it.data as LabsListModel
                                    if (value.result.data.labs.isNotEmpty())
                                        viewModel.passListToUI(value.result.data.labs)

                                    Log.d("Api Response", value.toString())
                                } catch (e: Exception) {

                                }
                            }
                            Task.DELETE -> {

                            }

                        }

                    }

                }
                is DataState.Error -> {

                    Log.d("Api Response", "ERROR ${it.exception.toString()}");
                }
                is DataState.Loading -> {
                    Log.d("Api Response", "LOADING $it");


                }
            }
        }


    }


    inner class ClickEvents() {

        fun openMap() {
            var intent =
                Intent(activity, LocationFetchFromMapActivity::class.java)
            startActivity(intent)
        }
    }


}


