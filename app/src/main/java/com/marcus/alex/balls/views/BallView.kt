package com.marcus.alex.balls.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import com.marcus.alex.balls.Vector

class BallView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    val ballArray: ArrayList<Ball> = ArrayList()
    var i = 0

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
        //Add new balls to the view
//        ballArray.add(Ball(250f, 750f, 200, Color.BLACK))
//        ballArray.add(Ball(1000f, 250f, 200, Color.RED))
//        ballArray.add(Ball(400f, 1200f, 200, Color.BLUE))
//        ballArray.add(Ball(670f, 780f, 200, Color.GRAY))
        //ballArray.add(Ball(Vector(550.0, 550.0), 300.0, Color.GREEN))
//        for (i in 1..30) {
//            ballArray.add(
//                Ball(
//                    Vector(i * 100.0, i * 100.0),
//                    70.0,
//                    colorArray[i % colorArray.size])
//            )
//        }
    }

    fun addBall() {
        i += 1
        ballArray.add(
            Ball(
                Vector(i * 100.0, i * 100.0),
                70.0,
                colorArray[i % colorArray.size]
            )
        )
    }


    fun reset() {
        ballArray.clear()
        i = 0
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //Draw the balls
        handleCollisions()

        for (ball in ballArray) {
            ball.move(canvas)

            canvas.drawOval(ball.oval!!, ball.paint)
        }
        invalidate()

    }

    private fun handleCollisions() {
        for (i in 0 until ballArray.size) {
            for (j in i + 1 until ballArray.size) {
                if (ballArray[i].willCollide(ballArray[j]))
                    ballArray[i].collide(ballArray[j])
            }
        }
    }
}
