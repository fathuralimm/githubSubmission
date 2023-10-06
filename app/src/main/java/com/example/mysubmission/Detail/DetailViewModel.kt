package com.example.mysubmission.Detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mysubmission.data.local.DbModule
import com.example.mysubmission.data.model.ItemsItem
import com.example.mysubmission.data.remote.SettingApi
import com.example.mysubmission.utils.Result
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailViewModel(private val database: DbModule): ViewModel() {
    val resultDetailUser = MutableLiveData<Result>()
    val resultFollowers = MutableLiveData<Result>()
    val resultFollowing = MutableLiveData<Result>()

    val resultAddFavorite = MutableLiveData<Boolean>()
    val resultDeleteFavorite = MutableLiveData<Boolean>()

    fun getDetail(username: String) {
        viewModelScope.launch {
            flow {
                val response = SettingApi
                    .githubService
                    .getDetailUser(username)

                emit(response)
            }.onStart {
                resultDetailUser.value = Result.Loading(true)
            }.onCompletion {
                resultDetailUser.value = Result.Loading(false)
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                resultDetailUser.value = Result.Error(it)
            }.collect {
                resultDetailUser.value = Result.Success(it)
            }
        }
    }

    fun getFollowers(username: String) {
        viewModelScope.launch {
            flow {
                val response = SettingApi
                    .githubService
                    .getFollowers(username)

                emit(response)
            }.onStart {
                resultFollowers.value = Result.Loading(true)
            }.onCompletion {
                resultFollowers.value = Result.Loading(false)
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                resultFollowers.value = Result.Error(it)
            }.collect {
                resultFollowers.value = Result.Success(it)
            }
        }
    }

    fun getFollowing(username: String) {
        viewModelScope.launch {
            flow {
                val response = SettingApi
                    .githubService
                    .getFollowing(username)

                emit(response)
            }.onStart {
                resultFollowing.value = Result.Loading(true)
            }.onCompletion {
                resultFollowing.value = Result.Loading(false)
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                resultFollowing.value = Result.Error(it)
            }.collect {
                resultFollowing.value = Result.Success(it)
            }
        }
    }
    private var isFavorite = false
    fun saveUser(item: ItemsItem?) {
        viewModelScope.launch {
            item?.let {
                if(isFavorite) {
                    database.userDao.delete(item)
                    resultDeleteFavorite.value = true
                }else {
                    database.userDao.insert(item)
                    resultAddFavorite.value = true
                }
            }
            isFavorite = !isFavorite
        }
    }

    fun findFav(id: Int, listenFav: () -> Unit) {
        viewModelScope.launch {
            val user = database.userDao.findById(id)
            if(user!= null) {
                listenFav()
                isFavorite = true
            }
        }
    }
    class Factory(private val database: DbModule): ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = DetailViewModel(database) as T
    }
}