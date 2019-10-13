package com.example.desafio_dti.shared.api

import com.example.desafio_dti.BuildConfig
import com.example.desafio_dti.login.model.LoginRequest
import com.example.desafio_dti.login.model.LoginResponse
import com.example.desafio_dti.login.model.SignUpRequest
import com.example.desafio_dti.login.model.SignUpResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface MainApi {

    @POST("/newUser")
    fun setNewUser(@Body signUpRequest: SignUpRequest, @Query(value = "simulateError")simulateError:Boolean): Call<SignUpResponse>

    @POST("/login")
    fun login(@Body loginRequest: LoginRequest, @Query(value = "simulateError")simulateError:Boolean): Call<LoginResponse>

    companion object {
        fun getMainApi(): MainApi {

            val baseUrl = "https://desafio-dti.herokuapp.com//"
            val clientBuilder = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)

            if (BuildConfig.DEBUG) {
                val logger: HttpLoggingInterceptor = HttpLoggingInterceptor()
                logger.level = HttpLoggingInterceptor.Level.BODY

                clientBuilder.addInterceptor(logger)
            }

            val client = clientBuilder.build()

            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(MainApi::class.java)
        }
    }

}