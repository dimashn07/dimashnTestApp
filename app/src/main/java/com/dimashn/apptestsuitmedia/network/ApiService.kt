package com.dimashn.apptestsuitmedia.network

import com.dimashn.apptestsuitmedia.data.UserResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object{
        const val LIST_USER = "users"

        private const val BASE_URL = "https://reqres.in/api/"
        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        fun create(): ApiService {
            return retrofit.create(ApiService::class.java)
        }
    }

    @GET(LIST_USER)
    fun getUsers(@Query("page") page: Int, @Query("per_page") perPage: Int): Call<UserResponse>
}