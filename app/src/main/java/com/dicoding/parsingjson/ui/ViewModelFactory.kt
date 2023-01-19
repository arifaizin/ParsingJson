package com.dicoding.parsingjson.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.parsingjson.data.UserRepository
import com.dicoding.parsingjson.di.Injection
import com.dicoding.parsingjson.ui.detail.DetailUserViewModel
import com.dicoding.parsingjson.ui.favorite.FavoriteUserViewModel
import com.dicoding.parsingjson.ui.list.MainViewModel
import com.dicoding.parsingjson.ui.setting.SettingViewModel

class ViewModelFactory private constructor(private val userRepository: UserRepository) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(DetailUserViewModel::class.java)) {
            return DetailUserViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(FavoriteUserViewModel::class.java)) {
            return FavoriteUserViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}