package com.rickyslash.githubuserapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFollowsViewModel: ViewModel() {

    private val _listFollows = MutableLiveData<List<DetailFollowsResponseItem>?>()
    val listFollows: LiveData<List<DetailFollowsResponseItem>?> = _listFollows

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    companion object {
        private val TAG = DetailFollowsViewModel::class.java.simpleName
    }

    fun getFollows(username: String, index: Int) {
        _isLoading.value = true
        _isError.value = false
        lateinit var client: Call<List<DetailFollowsResponseItem>>
        if (index == 1) {
            client = ApiConfig.getApiService().getFollowers(username)
        } else if (index == 2) {
            client = ApiConfig.getApiService().getFollowing(username)
        }
        client.enqueue(object : Callback<List<DetailFollowsResponseItem>> {
            override fun onResponse(
                call: Call<List<DetailFollowsResponseItem>>,
                response: Response<List<DetailFollowsResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listFollows.value = responseBody
                    }
                } else {
                    _isError.value = true
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<DetailFollowsResponseItem>>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

}