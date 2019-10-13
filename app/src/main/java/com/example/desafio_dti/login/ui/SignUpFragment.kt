package com.example.desafio_dti.login.ui


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment

import com.example.desafio_dti.R
import com.example.desafio_dti.login.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SignUpFragment : Fragment(), TextWatcher {
    private val mLoginViewModel by sharedViewModel<LoginViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()

    }

    private fun setupListeners() {
        materialButton_signupFragment_signup.setOnClickListener {
            mLoginViewModel.saveLoginAndPassword(
                editText_signupFragment_login.text.toString(),
                editText_signupFragment_password.text.toString()
            )
            NavHostFragment.findNavController(this@SignUpFragment)
                .navigate(R.id.action_signUpFragment_to_signUpFinishFragment)
        }

        materialButton_signupFragment_signup.setOnLongClickListener {
            val bundle = bundleOf("simulateError" to true)
            NavHostFragment.findNavController(this@SignUpFragment)
                .navigate(R.id.action_signUpFragment_to_signUpFinishFragment, bundle)
            true
        }

        imageView_signUpFragment_goBack.setOnClickListener{
            activity?.onBackPressed()
        }
    }

    override fun afterTextChanged(s: Editable?) {
        val credentialsAreValid = checkFormCredentialsValidity()
        materialButton_signupFragment_signup.isEnabled = credentialsAreValid
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // ignore
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // ignore
    }

    private fun checkFormCredentialsValidity(): Boolean {
        return !editText_signupFragment_login.text.isNullOrBlank() && !editText_signupFragment_password.text.isNullOrBlank()
    }


}
