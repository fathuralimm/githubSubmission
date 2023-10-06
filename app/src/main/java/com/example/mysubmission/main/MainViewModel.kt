package com.example.mysubmission.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mysubmission.data.local.SettingPreferences
import com.example.mysubmission.data.remote.SettingApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import com.example.mysubmission.utils.Result

class MainViewModel(private val preferences: SettingPreferences): ViewModel() {

    val resultUser = MutableLiveData<Result>()

    fun getUser(username: String) {
        viewModelScope.launch {
            flow {
                val response = SettingApi
                    .githubService
                    .getSearchUser(mapOf(
                        "q" to username,
                        "per_page" to 10
                    ))

                emit(response)
            }.onStart {
                resultUser.value = Result.Loading(true)
            }.onCompletion {
                resultUser.value = Result.Loading(false)
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                resultUser.value = Result.Error(it)
            }.collect {
                resultUser.value = Result.Success(it.items)
            }
        }
    }

    class Factory(private val preferences: SettingPreferences) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            MainViewModel(preferences) as T
    }

    fun getTheme() = preferences.getThemeSetting().asLiveData()

}