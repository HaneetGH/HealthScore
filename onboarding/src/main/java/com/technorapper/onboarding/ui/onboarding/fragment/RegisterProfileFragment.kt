package com.technorapper.onboarding.ui.onboarding.fragment


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.technorapper.onboarding.R
import com.technorapper.onboarding.base.BaseFragment
import com.technorapper.onboarding.constant.Task
import com.technorapper.onboarding.data.data_model.BasicResult
import com.technorapper.onboarding.databinding.FragmentProfileRegisterBinding
import com.technorapper.onboarding.domain.DataState
import com.technorapper.onboarding.ui.onboarding.MainListStateEvent
import com.technorapper.onboarding.ui.onboarding.OnBoardingViewModel
import com.technorapper.root.extension.userDataStore
import com.technorapper.root.proto.ProtoUserRepo
import com.technorapper.root.proto.ProtoUserRepoImpl
import com.technorapper.root.ui.list.RootActivity


class RegisterProfileFragment : BaseFragment() {
    private var userRepo: ProtoUserRepo? = null

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
        binding.etdob.setOnClickListener {
            viewModel.fetchTimeAndDate(requireActivity()).observe(requireActivity(), Observer {
                binding.etdob.text = it
            })
        }
    }


    override fun attachViewModel() {
        userRepo = ProtoUserRepoImpl(requireContext().userDataStore)
        viewModel.InjectDep(userRepo!!)
        viewModel.pushContext(requireActivity())
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
                            Task.UPDATE_ONBOARD -> {
                                try {
                                    val value =
                                        it.data as BasicResult
                                    Log.d("Result", value.toString())
                                    if(value.result.status_code==1){
                                        startActivity(
                                            Intent(
                                                activity,
                                                RootActivity::class.java
                                            )
                                        )
                                        requireActivity().finishAffinity()
                                    }
                                    /*value.addOnCompleteListener {
                                        if (it.isSuccessful) {

                                        }
                                    }
                                    value.addOnFailureListener { }*/
                                } catch (e: Exception) {

                                }
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


        fun saveData(userDob: String, userName: String, userEmail: String, userProfession: String) {
            if (userDob.isEmpty()) {
                binding.errorMessageDOB.text = "Please fill this"
                return
            }
            if (userName.isEmpty()) {
                binding.errorMessageName.text = "Please fill this"
                return
            }
            if (userEmail.isEmpty()) {
                binding.errorMessageEmail.text = "Please fill this"
                return
            }
            if (userProfession.isEmpty()) {
                binding.errorMessageOtpProfessional.text = "Please fill this"
                return
            }


            viewModel.setStateEvent(
                MainListStateEvent.UpdateUserInfo(
                    userDob,
                    "0,0",
                    userName,
                    userEmail,
                    userProfession
                )
            )

        }

    }


}


