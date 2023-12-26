package com.dimashn.apptestsuitmedia.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dimashn.apptestsuitmedia.data.User
import com.dimashn.apptestsuitmedia.data.UserResponse
import com.dimashn.apptestsuitmedia.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThirdScreenViewModel : ViewModel() {

    private val _listUsers: MutableLiveData<List<User>> = MutableLiveData()
    val listUsers: LiveData<List<User>> = _listUsers

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val apiService = ApiService.create()

    private var currentPage = 1

    fun loadData(page: Int, perPage: Int) {
        _isLoading.value = true

        apiService.getUsers(page, perPage).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUsers.value = response.body()?.data
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d("Failure", t.message.toString())
            }
        })
    }

    fun refreshData() {
        _isLoading.value = true
        currentPage = 1
        loadData(page = 1, perPage = 10)
    }

    fun loadNextPage() {
        _isLoading.value = true
        currentPage++
        loadData(page = currentPage, perPage = 10)

    }

}
