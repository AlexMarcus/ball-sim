package com.marcus.alex.balls.views

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import com.marcus.alex.balls.Vector
import kotlin.random.Random

class Ball(
    var position: Vector,
    var radius: Double,
    color: Int,
    var oval: RectF? = null,
    var velocity: Vector = Vector(
        Random.nextDouble() * 10,
        Random.nextDouble() * 10
    ),
    val paint: Paint = Paint()

) {

    init {
        paint.color = color
    }

    fun move(canvas: Canvas) {
        position += velocity

        val x = position.x
        val y = position.y

        oval = RectF(
            (x - radius).toFloat(), (y - radius).toFloat(),
            (x + radius).toFloat(), (y + radius).toFloat()
        )

        //Do we need to bounce next time?
        val bounds = Rect()
        oval?.roundOut(bounds) ///store our int bounds



        if (!canvas.clipBounds.contains(bounds)) {
            val ballMinX: Double = radius
            val ballMinY: Double = radius
            val ballMaxX: Double = canvas.width - radius
            val ballMaxY: Double = canvas.height - radius
            if (position.x < ballMinX) {
                velocity.x *= -1
                position.x = ballMinX // Re-position the ball at the edge
            } else if (position.x > ballMaxX) {
                velocity.x *= -1
                position.x = ballMaxX
            }
            // May cross both x and y bounds
            // May cross both x and y bounds
            if (position.y < ballMinY) {
                velocity.y *= -1
                position.y = ballMinY
            } else if (position.y > ballMaxY) {
                velocity.y -= 5
                velocity.y *= -1
                position.y = ballMaxY
            }
        }

        velocity.y += 5
    }

    fun willCollide(other: Ball): Boolean {
        val distance = position.distTo(other.position)
        val minDistance = other.radius + radius

        return distance <= minDistance
    }

    fun collide(ball: Ball) {

//        val delta: Vector2d = position.subtract(ball.position)
//        val d: Float = delta.getLength()
//        // minimum translation distance to push balls apart after intersecting
//        // minimum translation distance to push balls apart after intersecting
//        val mtd: Vector2d = delta.multiply((getRadius() + ball.getRadius() - d) / d)
//
//
//        // resolve intersection --
//        // inverse mass quantities
//
//
//        // resolve intersection --
//        // inverse mass quantities
//        val im1: Float = 1 / getMass()
//        val im2: Float = 1 / ball.getMass()
//
//        // push-pull them apart based off their mass
//
//        // push-pull them apart based off their mass
//        position = position.add(mtd.multiply(im1 / (im1 + im2)))
//        ball.position = ball.position.subtract(mtd.multiply(im2 / (im1 + im2)))
//
//        // impact speed
//
//        // impact speed
//        val v: Vector2d = this.velocity.subtract(ball.velocity)
//        val vn: Float = v.dot(mtd.normalize())
//
//        // sphere intersecting but moving away from each other already
//
//        // sphere intersecting but moving away from each other already
//        if (vn > 0.0f) return
//
//        // collision impulse
//
//        // collision impulse
//        val i: Float = -(1.0f + Constants.restitution) * vn / (im1 + im2)
//        val impulse: Vector2d = mtd.normalize().multiply(i)
//
//        // change in momentum
//
//        // change in momentum
//        this.velocity = this.velocity.add(impulse.multiply(im1))
//        ball.velocity = ball.velocity.subtract(impulse.multiply(im2))

        if((this.velocity - ball.velocity).dot((this.position - ball.position)) < 0) {
            val tempVel = velocity
            this.velocity = ball.velocity
            ball.velocity = tempVel
        }
    }
}