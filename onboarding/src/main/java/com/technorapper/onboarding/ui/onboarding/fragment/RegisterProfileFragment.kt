package com.technorapper.onboarding.ui.onboarding.fragment


import com.technorapper.root.ui.list.RootActivity
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.technorapper.onboarding.R
import com.technorapper.onboarding.base.BaseFragment
import com.technorapper.onboarding.constant.Task
import com.technorapper.onboarding.databinding.ActivityRegisterBinding
import com.technorapper.onboarding.databinding.FragmentProfileRegisterBinding
import com.technorapper.onboarding.domain.DataState
import com.technorapper.onboarding.ui.onboarding.MainListStateEvent
import com.technorapper.onboarding.ui.onboarding.OnBoardingViewModel
import java.util.concurrent.TimeUnit


class RegisterProfileFragment : BaseFragment() {


    private var mAuth: FirebaseAuth? = null;
    private val viewModel by viewModels<OnBoardingViewModel>()
    lateinit var binding: FragmentProfileRegisterBinding


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
            R.layout.fragment_profile_register,
            container,
            false
        )

        binding.handler = ClickEvents()
        setEvents();

        return binding.root
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
                            Task.ONBOARD -> {
                                try {
                                    val value =
                                        it.data as com.google.android.gms.tasks.Task<AuthResult>
                                    value.addOnCompleteListener {
                                    }
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



        fun saveData() {

        }
    }


}


