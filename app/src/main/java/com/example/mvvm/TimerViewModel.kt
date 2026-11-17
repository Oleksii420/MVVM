package com.example.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private const val TAG = "Timer"
private const val DEFAULT_SECONDS = 30L

class TimerViewModel : ViewModel() {

    private val timerModel = TimerModel()

    private val _timerLiveData = MutableLiveData(DEFAULT_SECONDS)
    val timerLiveData: LiveData<Long> = _timerLiveData

    private val _isTimerRunning = MutableLiveData(false)
    val isTimerRunning: LiveData<Boolean> = _isTimerRunning

    init {
        Log.d(TAG, "TimerViewModel створено")
    }

    fun startTimer(userSeconds: Long?) {
        val seconds = userSeconds?.takeIf { it > 0 } ?: DEFAULT_SECONDS

        _timerLiveData.value = seconds
        _isTimerRunning.value = true

        timerModel.start(
            seconds = seconds,
            onTick = { left ->
                Log.d(TAG, "onTick: $left")
                _timerLiveData.value = left
            },
            onFinish = {
                Log.d(TAG, "onFinish")
                _timerLiveData.value = 0
                _isTimerRunning.value = false
            }
        )
    }

    fun resetTimer() {
        timerModel.stop()
        _timerLiveData.value = DEFAULT_SECONDS
        _isTimerRunning.value = false
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared: ViewModel знищено, таймер зупинено")
        timerModel.stop()
    }
}