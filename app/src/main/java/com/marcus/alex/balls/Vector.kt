package com.marcus.alex.balls

import kotlin.math.*

/**
 * Class for representing 2D vectors (x and y).
 */
data class Vector @JvmOverloads constructor(
    var x: Double = 0.0,
    var y: Double = 0.0
) {
    companion object {
        @JvmStatic
        fun polar(r: Double, theta: Double) = Vector(r * cos(theta), r * sin(theta))
    }

    fun norm() = sqrt(x * x + y * y)

    fun angle() = Angle.norm(atan2(y, x))

    infix fun angleBetween(other: Vector) = acos((this dot other) / (norm() * other.norm()))

    operator fun plus(other: Vector) =
        Vector(x + other.x, y + other.y)

    operator fun minus(other: Vector) =
        Vector(x - other.x, y - other.y)

    operator fun times(scalar: Double) = Vector(scalar * x, scalar * y)

    operator fun div(scalar: Double) = Vector(x / scalar, y / scalar)

    operator fun unaryMinus() = Vector(-x, -y)

    infix fun dot(other: Vector) = x * other.x + y * other.y

    infix fun distTo(other: Vector) = (this - other).norm()

    infix fun projectOnto(other: Vector) = other * (this dot other) / (other dot other)

    fun rotated(angle: Double): Vector {
        val newX = x * cos(angle) - y * sin(angle)
        val newY = x * sin(angle) + y * cos(angle)
        return Vector(newX, newY)
    }

    override fun toString() = String.format("(%.3f, %.3f)", x, y)
}

operator fun Double.times(vector: Vector) = vector.times(this)

operator fun Double.div(vector: Vector) = vector.div(this)

fun vectorOf(x: Double, y:Double) = Vector(x, y)