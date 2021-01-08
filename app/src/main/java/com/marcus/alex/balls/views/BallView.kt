package com.marcus.alex.balls.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class BallView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    var ballArray: List<Ball> = listOf()
    var canvasRect: Rect = Rect()
    private val frameRateHandler = Handler()

    private val activity: FragmentActivity by lazy {
        try {
            context as FragmentActivity
        } catch (exception: ClassCastException) {
            throw ClassCastException("Please ensure that the provided Context is a valid FragmentActivity")
        }
    }

    private val viewModel: BallViewModel by lazy {
        ViewModelProvider(activity).get(BallViewModel::class.java)
    }

    init {
        viewModel.ballLiveData.observe(
            activity, Observer {
                ballArray = it
            }
        )
    }

    fun addBall() {
        viewModel.addBall()
    }

    fun reset() {
        viewModel.reset()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // update canvas in vm
        canvas.getClipBounds(canvasRect)
        viewModel.update(canvasRect)

        // draw balls
        for (ball in ballArray) {
            ball.oval?.let { canvas.drawOval(it, ball.paint) }
        }

        frameRateHandler.postDelayed({ invalidate() }, 32)
    }
}
