package com.example.desafio_dti.login.ui


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
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class LoginFinishFragment : Fragment() {
    private val mLoginViewModel by sharedViewModel<LoginViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_finish, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tryToLogin()
        setupDoneAnimation()
        setupListeners()
    }

    private fun setupListeners() {
        button_loginFinishFragment_cancel.setOnClickListener {
            onCancelLogin()
        }

        button_loginFinishFragment_retry.setOnClickListener {
            tryToLogin()
        }

    }

    private fun onCancelLogin() {
        activity?.onBackPressed()
    }

    private fun tryToLogin() {
        setOnLoadingState()
        val simulaError = arguments?.getBoolean("simulateError") ?: false
        mLoginViewModel.tryToLogin(simulaError).observe(viewLifecycleOwner, Observer { operation ->
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
        container_loginFinishFragment_errorView.visibility = View.GONE
        container_loginFinishFragment_successView.visibility = View.VISIBLE
        container_loginFinishFragment_savingView.visibility = View.GONE

    }

    private fun setErrorState() {
        container_loginFinishFragment_errorView.visibility = View.VISIBLE
        container_loginFinishFragment_successView.visibility = View.GONE
        container_loginFinishFragment_savingView.visibility = View.GONE
    }

    private fun setOnLoadingState() {
        container_loginFinishFragment_errorView.visibility = View.GONE
        container_loginFinishFragment_successView.visibility = View.GONE
        container_loginFinishFragment_savingView.visibility = View.VISIBLE
    }

    private fun setupDoneAnimation() {
        animation_loginFinishFragment_savingDone.addAnimatorListener(object :
            Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
                //ignore
            }

            override fun onAnimationEnd(animation: Animator?) {
                onLoginSuccess()
            }

            override fun onAnimationCancel(animation: Animator?) {
                //ignore
            }

            override fun onAnimationStart(animation: Animator?) {
                //ignore
            }
        })
    }

    private fun onLoginSuccess() {
        //go to other main screen
        mLoginViewModel.clearLoginAndPassword()
        NavHostFragment.findNavController(this@LoginFinishFragment)
            .navigate(R.id.action_loginFinishFragment_to_mainFragment)
    }
}
