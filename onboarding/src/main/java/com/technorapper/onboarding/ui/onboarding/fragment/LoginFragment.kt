package com.technorapper.onboarding.ui.onboarding.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController


import com.technorapper.onboarding.R
import com.technorapper.onboarding.base.BaseFragment
import com.technorapper.onboarding.constant.Task
import com.technorapper.onboarding.data.data_model.LocationTable
import com.technorapper.onboarding.databinding.ActivityDateLoginBinding
import com.technorapper.onboarding.domain.DataState
import com.technorapper.onboarding.ui.onboarding.MainListStateEvent
import com.technorapper.onboarding.ui.onboarding.OnBoardingViewModel

class LoginFragment : BaseFragment() {


    private val viewModel by viewModels<OnBoardingViewModel>()
    lateinit var binding: ActivityDateLoginBinding


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
            R.layout.activity_date_login,
            container,
            false
        )
        binding.counter = 60.00

        binding.handler = ClickEvents()
        setEvents();

        return binding.root
    }


    private fun setEvents() {
        binding.registerBtn.setOnClickListener {
         //   it.findNavController().navigate(R.id.action_blankFragmentOne_to_registerFragment)

        }
    }


    override fun attachViewModel() {

        viewModel.uiState.observe(this, Observer { parse(it) })
    }

    override fun onResume() {
        super.onResume()

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


    inner class ClickEvents() {


    }


}


