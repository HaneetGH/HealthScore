package com.technorapper.root.ui.list.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil

import com.technorapper.root.R
import com.technorapper.root.base.BaseFragment
import com.technorapper.root.constant.Task
import com.technorapper.root.data.data_model.LocationTable

import com.technorapper.root.databinding.FragmentRootBinding
import com.technorapper.root.domain.DataState
import com.technorapper.root.ui.MainActivity
import com.technorapper.root.ui.list.ListActivityViewModel
import com.technorapper.root.ui.list.MainListStateEvent
import com.technorapper.root.ui.list.adapter.ListAdapter
import com.technorapper.root.ui.location.LocationFetchFromMapActivity
import com.technorapper.root.utils.ListDiffCallback

class ListFragment : BaseFragment() {


    private val viewModel by viewModels<ListActivityViewModel>()
    lateinit var binding: FragmentRootBinding
    lateinit var listAdapter: ListAdapter;
    private val listOfLocations: ArrayList<LocationTable> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_root,
            container,
            false
        )
        binding.counter = 60.00

        binding.handler = ClickEvents()
        setEvents();
        setAdapter();
        return binding.root
    }

    private fun setAdapter() {
        listAdapter = ListAdapter(listOfLocations, activity) { v, position ->
            when (v.id) {
                R.id.delete -> {
                    viewModel.setStateEvent(MainListStateEvent.DeleteItem(listOfLocations[position]))
                    listOfLocations.remove(listOfLocations[position])
                    listAdapter.notifyItemRemoved(position)

                }
                R.id.llMain -> {
                    val intent = Intent(activity, MainActivity::class.java)
                    intent.putExtra("lat", listOfLocations[position].Lat)
                    intent.putExtra("lng", listOfLocations[position].Lng)
                    startActivity(intent)
                }
            }


        }
        binding.adapter = listAdapter
    }

    private fun setEvents() {

    }


    override fun attachViewModel() {
        viewModel.setStateEvent(MainListStateEvent.FetchBookmark)
        viewModel.uiState.observe(this, Observer { parse(it) })
    }

    override fun onResume() {
        super.onResume()
        viewModel?.setStateEvent(MainListStateEvent.FetchBookmark)
    }

    private fun parse(it: DataState?) {

        if (it != null) {
            when (it) {
                is DataState.Success<*> -> {
                    Log.d("Api Response", "SUCCES");

                    if (it?.data != null) {
                        when (it.task) {
                            Task.FETCH -> {
                                try {
                                    val value = it.data as List<LocationTable>
                                    if (value.isNotEmpty())
                                        setData(value)
                                    else
                                        binding.isListHere = false
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

    fun setData(locationTable: List<LocationTable>) {
        binding.isListHere = true
        val diffCallback = ListDiffCallback(listOfLocations, locationTable)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listOfLocations.clear()
        listOfLocations.addAll(locationTable)
        diffResult.dispatchUpdatesTo(listAdapter)
    }


    inner class ClickEvents() {

        fun openMap() {
            var intent =
                Intent(activity, LocationFetchFromMapActivity::class.java)
            startActivity(intent)
        }
    }


}


