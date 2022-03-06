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
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.technorapper.onboarding.R
import com.technorapper.onboarding.base.BaseFragment
import com.technorapper.onboarding.constant.Task
import com.technorapper.onboarding.databinding.ActivityRegisterBinding
import com.technorapper.onboarding.domain.DataState
import com.technorapper.onboarding.ui.onboarding.MainListStateEvent
import com.technorapper.onboarding.ui.onboarding.OnBoardingViewModel
import java.util.concurrent.TimeUnit


class RegisterFragment : BaseFragment() {


    private var mAuth: FirebaseAuth? = null;
    private val viewModel by viewModels<OnBoardingViewModel>()
    lateinit var binding: ActivityRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance();

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.activity_register,
            container,
            false
        )
        binding.counter = 60.00

        binding.handler = ClickEvents()
        setEvents();
        binding.isOTPGenerated = false
        return binding.root
    }


    private fun setEvents() {

    }


    override fun attachViewModel() {
        viewModel.setStateEvent(MainListStateEvent.FetchBookmark)
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
                            Task.ONBOARD -> {
                                try {
                                    val value =
                                        it.data as com.google.android.gms.tasks.Task<AuthResult>
                                    value.addOnCompleteListener {
                                        if (it.isSuccessful) {


                                            // Create a new user with a first and last name

                                            viewModel.saveInPref(it.result.user?.uid,requireActivity())
                                            findNavController().navigate(R.id.action_registerFragment_to_registerProfileFragment)
                                            /**/
                                        } else
                                            binding.isOTPGenerated = false

                                    }

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

    private fun sendVerificationCode(number: String) {
        // this method is used for getting
        // OTP on user phone number.
        val options = PhoneAuthOptions.newBuilder(mAuth!!)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity()) // Activity (for callback binding)
            .setCallbacks(mCallBack) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val mCallBack = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.d(ContentValues.TAG, "onVerificationCompleted:$credential")
            // signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w(ContentValues.TAG, "onVerificationFailed", e)

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
            }
            if (binding.isOTPGenerated)
                binding.errorMessageOtp.text = e.message
            else
                binding.errorMessage.text = e.message
            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d(ContentValues.TAG, "onCodeSent:$verificationId")

            // Save verification ID and resending token so we can use them later
            storedVerificationId = verificationId
            binding.isOTPGenerated = true
            // resendToken = token
        }
    }


    private var storedVerificationId: String? = null
    private fun verifyCode(code: String) {
        viewModel.setStateEvent(MainListStateEvent.RegisterUser(code, storedVerificationId!!))
    }


    inner class ClickEvents() {

        fun register(phone: String) {
            var num = "+91$phone"
            sendVerificationCode(num)
        }

        fun verifyOTP(otp: String) {
            verifyCode(otp)
        }
    }


}


