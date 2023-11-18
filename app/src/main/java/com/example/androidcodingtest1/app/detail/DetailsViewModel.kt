package com.example.androidcodingtest1.app.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcodingtest1.app.detail.model.DetailItem
import com.example.androidcodingtest1.app.list.model.ListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    item: ListItem
) : ViewModel() {

    data class State(
        val item: DetailItem? = null
    )

    sealed class Effect {
        data object NavigateBack : Effect()
    }

    private val _uiState = MutableStateFlow(State())
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<Effect>()
    val uiEffect: SharedFlow<Effect> = _uiEffect.asSharedFlow()

    init {
        viewModelScope.launch {
            delay(2000)
            _uiState.value = State(DetailItem(title = item.text, id = item.id))
        }
    }

    override fun onCleared() {
        Log.i("Lalala", "onCleared: $this")
        super.onCleared()
    }
}
