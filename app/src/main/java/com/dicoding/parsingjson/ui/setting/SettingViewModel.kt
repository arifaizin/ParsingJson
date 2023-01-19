package com.dicoding.parsingjson.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.parsingjson.data.UserRepository
import kotlinx.coroutines.launch

class SettingViewModel(private val userRepository: UserRepository) : ViewModel() {
   fun getThemeSettings(): LiveData<Boolean> {
       return userRepository.getThemeSetting().asLiveData()
   }
 
   fun saveThemeSetting(isDarkModeActive: Boolean) {
       viewModelScope.launch {
           userRepository.saveThemeSetting(isDarkModeActive)
       }
   }
}