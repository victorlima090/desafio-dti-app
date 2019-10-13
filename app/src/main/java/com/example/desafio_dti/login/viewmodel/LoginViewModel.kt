package com.example.desafio_dti.login.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.desafio_dti.login.model.LoginRequest
import com.example.desafio_dti.login.model.SignUpRequest
import com.example.desafio_dti.shared.Operation
import com.example.desafio_dti.shared.api.MainApi
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

    fun tryToLogin(simulateError:Boolean):LiveData<Operation<Boolean>>{
        val loginOperation = MutableLiveData<Operation<Boolean>>()
        loginOperation.value = Operation.running()
        ioScope.launch {
            delay(1000)
            val loginRequest = LoginRequest(mLogin, mPassword)
            val response = MainApi.getMainApi().login(loginRequest, simulateError).execute()
            val operationSucceded = response.isSuccessful && response.body()?.errorMessage.isNullOrBlank()
            loginOperation.postValue(Operation.success(operationSucceded))
        }
        return loginOperation
    }

    fun tryToSignUp(simulateError:Boolean):LiveData<Operation<Boolean>>{
        val signUpOperation = MutableLiveData<Operation<Boolean>>()
        signUpOperation.value = Operation.running()
        ioScope.launch {
            delay(1000)
            val signUpRequest = SignUpRequest(mLogin, mPassword)
            val response = MainApi.getMainApi().setNewUser(signUpRequest, simulateError).execute()
            val operationSucceded = response.isSuccessful && response.body()?.errorMessage.isNullOrBlank()
            signUpOperation.postValue(Operation.success(operationSucceded))
        }
        return signUpOperation
    }

    fun clearLoginAndPassword(){
        mLogin = ""
        mPassword = ""
    }
}