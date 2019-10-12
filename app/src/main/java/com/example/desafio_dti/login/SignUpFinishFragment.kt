package com.example.desafio_dti.login


import android.animation.Animator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment

import com.example.desafio_dti.R
import com.example.desafio_dti.login.viewmodel.LoginViewModel
import com.example.desafio_dti.shared.Operation
import kotlinx.android.synthetic.main.fragment_login_finish.*
import kotlinx.android.synthetic.main.fragment_sign_up_finish.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SignUpFinishFragment : Fragment() {

    private val mLoginViewModel by sharedViewModel<LoginViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up_finish, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tryToSignUp()
        setupDoneAnimation()
        setupListeners()
    }

    private fun setupListeners() {
        button_signupFinishFragment_cancel.setOnClickListener {
            onCancelSignUp()
        }

        button_signupFinishFragment_retry.setOnClickListener {
            tryToSignUp()
        }

    }

    private fun onCancelSignUp() {
        NavHostFragment.findNavController(this@SignUpFinishFragment).navigate(R.id.action_signUpFinishFragment_to_loginFragment)
    }

    private fun tryToSignUp() {
        setOnLoadingState()
        mLoginViewModel.tryToSignUp().observe(viewLifecycleOwner, Observer { operation ->
            when (operation.status) {
                Operation.Status.DONE -> {
                    operation.data?.let { resultSuccess ->
                        if (resultSuccess) {
                            setDoneState()
                        } else {
                            setErrorState()
                        }
                    }
                }
                Operation.Status.ERROR -> {
                    setErrorState()
                }
                Operation.Status.RUNNING -> {
                    //ignore
                }
            }

        })
    }

    private fun setDoneState() {
        container_signupFinishFragment_errorView.visibility = View.GONE
        container_signupFinishFragment_successView.visibility = View.VISIBLE
        container_signupFinishFragment_savingView.visibility = View.GONE

    }

    private fun setErrorState() {
        container_signupFinishFragment_errorView.visibility = View.VISIBLE
        container_signupFinishFragment_successView.visibility = View.GONE
        container_signupFinishFragment_savingView.visibility = View.GONE
    }

    private fun setOnLoadingState() {
        container_signupFinishFragment_errorView.visibility = View.GONE
        container_signupFinishFragment_successView.visibility = View.GONE
        container_signupFinishFragment_savingView.visibility = View.VISIBLE
    }

    private fun setupDoneAnimation() {
        animation_signupFinishFragment_savingDone.addAnimatorListener(object :
            Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
                //ignore
            }

            override fun onAnimationEnd(animation: Animator?) {
                onSignUpSuccess()
            }

            override fun onAnimationCancel(animation: Animator?) {
                //ignore
            }

            override fun onAnimationStart(animation: Animator?) {
                //ignore
            }
        })
    }

    private fun onSignUpSuccess() {
        activity?.onBackPressed()
    }


}
