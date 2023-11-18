package com.example.androidcodingtest1.app.list

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcodingtest1.app.list.model.ListItem
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.random.Random

class ListViewModel : ViewModel() {

    data class State(
        val list: List<ListItem> = listOf()
    )

    sealed class Effect {
        data class NavigateToDetail(val item: ListItem) : Effect()
    }

    private val _uiState = MutableStateFlow(State())
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<Effect>()
    val uiEffect: SharedFlow<Effect> = _uiEffect.asSharedFlow()

    init {
        _uiState.value = State(list = getItems())
        viewModelScope.launch {
            delay(2000)
            val oldList = _uiState.value.list
            val newList = mutableListOf<ListItem>()
            for (i in 0..100) {
                val item = if (i % 4 == 0) {
                    oldList[i].copy(text = "item ${i * 10}")
                } else {
                    oldList[i]
                }
                newList.add(item)
            }
            _uiState.value = _uiState.value.copy(list = newList)
        }
        viewModelScope.launch {
            launch {
                delay(200)
                Log.i("Lalala", "task 1")
            }

            coroutineScope {
                launch {
                    delay(500)
                    Log.i("Lalala", "task 2")
                }
                delay(100)
                Log.i("Lalala", "task 3")
            }

            Log.i("Lalala", "task 4")
        }
    }

    fun onItemClick(item: ListItem) {
        viewModelScope.launch {
            _uiEffect.emit(Effect.NavigateToDetail(item))
        }
    }

    private fun getItems(): List<ListItem> {
        val list = mutableListOf<ListItem>()
        val rnd = Random.Default
        for (i in 0..100) {
            val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            list.add(ListItem(id = UUID.randomUUID().toString(), text = "Item $i", color))
        }
        return list
    }
}
