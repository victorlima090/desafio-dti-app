package com.example.desafio_dti.login

import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment

import com.example.desafio_dti.R
import com.example.desafio_dti.login.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class LoginFragment : Fragment(), TextWatcher {

    private val mLoginViewModel by sharedViewModel<LoginViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupView()

    }

    private fun setupView(){
        editText_loginFragment_login.text = SpannableStringBuilder(mLoginViewModel.getLogin())
        editText_loginFragment_password.text = SpannableStringBuilder(mLoginViewModel.getPassword())
    }

    override fun afterTextChanged(s: Editable?) {
        val credentialsAreValid = checkFormCredentialsValidity()
        materialButton_loginFragment_login.isEnabled = credentialsAreValid
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // ignore
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // ignore
    }

    private fun checkFormCredentialsValidity(): Boolean {
        return !editText_loginFragment_login.text.toString().isBlank() && !editText_loginFragment_password.text.toString().isBlank()
    }

    private fun setupListeners() {
        materialButton_loginFragment_login.setOnClickListener {
            tryToLogin()
        }

        materialButton_loginFragment_signUp.setOnClickListener {
            goToSignUp()
        }
    }

    private fun goToSignUp() {
        NavHostFragment.findNavController(this@LoginFragment)
            .navigate(R.id.action_loginFragment_to_signUpFragment)
    }

    private fun tryToLogin() {

        mLoginViewModel.saveLoginAndPassword(
            editText_loginFragment_login.text.toString(),
            editText_loginFragment_password.text.toString()
        )
        NavHostFragment.findNavController(this@LoginFragment)
            .navigate(R.id.action_loginFragment_to_loginFinishFragment)
    }


}
