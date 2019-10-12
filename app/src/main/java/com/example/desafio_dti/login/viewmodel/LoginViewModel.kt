package com.example.desafio_dti.login.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.desafio_dti.shared.Operation
import kotlinx.coroutines.*

class LoginViewModel(androidApplication: Application):AndroidViewModel(androidApplication) {
    private var mLogin: String = ""
    private var mPassword:String = ""

    private val viewModelJob = SupervisorJob()
    private val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    fun saveLoginAndPassword(login:String, password:String){
        mLogin = login
        mPassword = password
    }

    fun getLogin() = mLogin

    fun getPassword() = mPassword

    fun tryToLogin():LiveData<Operation<Boolean>>{
        val loginOperation = MutableLiveData<Operation<Boolean>>()
        loginOperation.value = Operation.running()
        ioScope.launch {
            //try to login
            delay(3000)
            loginOperation.postValue(Operation.error("", null))
        }
        return loginOperation
    }

    fun tryToSignUp():LiveData<Operation<Boolean>>{
        val signUpOperation = MutableLiveData<Operation<Boolean>>()
        signUpOperation.value = Operation.running()
        ioScope.launch {
            //try to login
            delay(2000)
            signUpOperation.postValue(Operation.error("", null))
        }
        return signUpOperation
    }

    fun clearLoginAndPassword(){
        mLogin = ""
        mPassword = ""
    }
}