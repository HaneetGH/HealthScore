package com.technorapper.root.ui.list.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.technorapper.root.R
import com.technorapper.root.base.BaseFragment
import com.technorapper.root.constant.Task
import com.technorapper.root.databinding.FragmentBlankTwoBinding
import com.technorapper.root.domain.DataState
import com.technorapper.root.ui.list.ListActivityViewModel
import com.technorapper.root.ui.list.MainListStateEvent

class SettingFragment : BaseFragment() {


    private val viewModel by viewModels<ListActivityViewModel>()
    lateinit var binding: FragmentBlankTwoBinding

    lateinit var viewOptiom: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_blank_two,
            container,
            false
        )

        binding.handler = ClickEvents()
        binding.swich.setOnCheckedChangeListener { compoundButton, b ->
            viewModel.setStateEvent(
                MainListStateEvent.UpdateUnit(b)
            )
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.viewOptiom = view
    }

    override fun attachViewModel() {
        viewModel.setStateEvent(MainListStateEvent.FetchDefault)
        viewModel.uiState.observe(this, Observer { parse(it) })
    }

    private fun parse(it: DataState?) {

        if (it != null) {
            when (it) {
                is DataState.Success<*> -> {
                    Log.d("Api Response", "SUCCES");

                    if (it?.data != null) {
                        when (it.task) {
                            Task.NUKE -> {
                                Toast.makeText(activity, "Success", Toast.LENGTH_LONG).show()
                            }
                            Task.DEFAULT -> {
                                val value = it.data as String

                                binding.swich.isChecked = value.equals("imperial", false)
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

    override fun onResume() {
        super.onResume()

    }


    inner class ClickEvents() {

        fun reset() {
            viewModel.setStateEvent(MainListStateEvent.Reset)
        }

        fun help() {
            var navController: NavController = Navigation.findNavController(viewOptiom)
            navController.navigate(R.id.helpFragment)
        }

    }


}


