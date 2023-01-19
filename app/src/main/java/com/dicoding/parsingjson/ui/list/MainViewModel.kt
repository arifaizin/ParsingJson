package com.dicoding.parsingjson.ui.list

import androidx.lifecycle.*
import com.dicoding.parsingjson.data.UserRepository
import com.dicoding.parsingjson.data.model.ItemsItem
import kotlinx.coroutines.launch

sealed interface UserUiState {
    data class Success(val listUser: List<ItemsItem>) : UserUiState
    object Error : UserUiState
    object Loading : UserUiState
}

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _uiState = MutableLiveData<UserUiState>()
    val uiState: LiveData<UserUiState> = _uiState

    init {
        searchUser("arif")
    }

    fun searchUser(query: String) {
        viewModelScope.launch {
            _uiState.value = UserUiState.Loading
            try {
                _uiState.value = UserUiState.Success(userRepository.getListUsers(query).items)
            } catch (e: Exception) {
                _uiState.value = UserUiState.Error
            }
        }
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return userRepository.getThemeSetting().asLiveData()
    }
}