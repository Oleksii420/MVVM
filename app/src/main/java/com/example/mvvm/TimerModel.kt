package com.example.mvvm

import android.os.CountDownTimer

class TimerModel {

    private var countDownTimer: CountDownTimer? = null

    fun start(seconds: Long, onTick: (Long) -> Unit, onFinish: () -> Unit) {
        stop()

        countDownTimer = object : CountDownTimer(seconds * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                onTick.invoke(millisUntilFinished / 1000)
            }

            override fun onFinish() {
                countDownTimer = null
                onFinish.invoke()
            }
        }.start()
    }

    fun stop() {
        countDownTimer?.cancel()
        countDownTimer = null
    }
}