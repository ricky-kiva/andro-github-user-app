package com.rickyslash.githubuserapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel: ViewModel() {

    private val _displayname = MutableLiveData<String>()
    val displayname: LiveData<String> = _displayname

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _avatar = MutableLiveData<String>()
    val avatar: LiveData<String> = _avatar

    private val _followers = MutableLiveData<Int>()
    val followers: LiveData<Int> = _followers

    private val _following = MutableLiveData<Int>()
    val following: LiveData<Int> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private val TAG = DetailUserViewModel::class.java.simpleName
    }

    fun displayUser(username: String = "ricky-kiva") {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _displayname.value = responseBody.name
                        _username.value = responseBody.login
                        _avatar.value = responseBody.avatarUrl
                        _followers.value = responseBody.followers
                        _following.value = responseBody.following
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

}