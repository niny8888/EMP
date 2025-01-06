package fri.emp.odmevko.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fri.emp.odmevko.MyData
import fri.emp.odmevko.repository.MusicRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MusicViewModel(private val repository: MusicRepository) : ViewModel() {

    private val _musicData = MutableStateFlow<MyData?>(null)
    val musicData: StateFlow<MyData?> get() = _musicData

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun searchMusic(query: String) {
        viewModelScope.launch {
            repository.searchMusic(query,
                onSuccess = { data ->
                    _musicData.value = data
                },
                onError = { errorMsg ->
                    _error.value = errorMsg
                })
        }
    }
}
