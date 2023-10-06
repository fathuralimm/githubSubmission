package com.example.mysubmission.Favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mysubmission.data.local.DbModule

class FavoriteViewModel(private val dbModule: DbModule): ViewModel() {

    fun getFavoriteUser() = dbModule.userDao.loadAll()
    class Factory(private val db: DbModule): ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = FavoriteViewModel(db) as T
    }
}