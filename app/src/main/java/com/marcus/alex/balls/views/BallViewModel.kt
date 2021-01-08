package com.marcus.alex.balls.views

import android.graphics.Color
import android.graphics.Rect
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcus.alex.balls.Vector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.random.Random

class BallViewModel : ViewModel() {

    val ballLiveData: MutableLiveData<List<Ball>> = MutableLiveData(listOf())
    private var ballArray: ArrayList<Ball> = arrayListOf()
    private var canvasRect: Rect = Rect()

    private var lastTime: Long = System.currentTimeMillis()

    val mutex = Mutex()

    private val colorArray = arrayOf(
        Color.BLACK,
        Color.BLUE,
        Color.CYAN,
        Color.DKGRAY,
        Color.GRAY,
        Color.GREEN,
        Color.MAGENTA,
        Color.RED,
        Color.YELLOW,
    )

    init {
        runLoop()
    }

    private fun runLoop() {
        viewModelScope.launch(Dispatchers.IO) {
            handleBallUpdates()
            launch(Dispatchers.Main) { update() }
        }
    }

    private suspend fun handleBallUpdates() {

        val currentTime = System.currentTimeMillis()
        val dt = (currentTime - lastTime) / 1000.0
        lastTime = currentTime

        mutex.withLock {
            moveBalls(dt)
            handleCollisions()
        }
    }

    fun addBall() {
        viewModelScope.launch(Dispatchers.IO){
            addBallBackground()
        }
    }

    private suspend fun addBallBackground() {
        mutex.withLock {
            ballArray.apply {
                add(
                    Ball(
                        Vector(
                            70.0 + (canvasRect.width() - 140.0) * Random.nextDouble(),
                            0.0
                        ),
                        70.0,
                        colorArray[Random.nextInt(colorArray.size)]
                    )
                )
            }
        }
    }

    private fun update() {
        ballLiveData.value = ballArray
        runLoop()
    }

    private fun moveBalls(dt: Double) {
        for (ball in ballArray) {
            ball.move(dt, canvasRect)
        }
    }

    private fun handleCollisions() {
        for (i in 0 until ballArray.size) {
            for (j in i + 1 until ballArray.size) {
                if (ballArray[i].willCollide(ballArray[j]))
                    ballArray[i].collide(ballArray[j])
            }
        }
    }

    fun update(canvasRect: Rect) {
        this.canvasRect = canvasRect
    }

    fun reset() {
        ballArray = arrayListOf()
    }
}