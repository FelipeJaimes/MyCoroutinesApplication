package com.example.mycoroutinesapplication

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel (val ioDispatcher: CoroutineDispatcher = Dispatchers.IO): ViewModel() {

    private val _loginResult = MutableLiveData<Boolean>()
    private val _progressBarVisible = MutableLiveData<Boolean>()
    val loginResult : LiveData<Boolean> get() = _loginResult
    val progressBarVisible: LiveData<Boolean> get() = _progressBarVisible

    fun onSubmitClicked(username: String, password: String){
        viewModelScope.launch {
            _progressBarVisible.value = true
            _loginResult.value = withContext(ioDispatcher){
                validateLogin(username, password)
            }
            _progressBarVisible.value = false
        }
    }

    private fun validateLogin(username: String, password: String) : Boolean{
        Thread.sleep(2000)
        return username.isNotEmpty() && password.isNotEmpty()
    }

}