package com.example.raytracer

import android.graphics.Color
import kotlin.math.abs
import kotlin.math.sqrt
import kotlin.random.Random
import kotlin.math.min

data class Vec3(var x: Float = 0.0f, var y: Float = 0.0f, var z: Float = 0.0f) {
    inline var r: Float
        get() = x
        set(value) {
            x = value
        }

    inline var g: Float
        get() = y
        set(value) {
            y = value
        }

    inline var b: Float
        get() = z
        set(value) {
            z = value
        }


    operator fun plus(v: Float) = Vec3(x + v, x + y, x + z)
    operator fun plus(v: Vec3) = Vec3(x + v.x, y + v.y, z + v.z)
    operator fun plusAssign(v: Float) {
        this.x += v
        this.y += v
        this.z += z
    }

    operator fun plusAssign(v: Vec3) {
        this.x += v.x
        this.y += v.y
        this.z += v.z
    }

    operator fun minus(v: Float) = Vec3(x - v, x - y, x - z)
    operator fun minus(v: Vec3) = Vec3(x - v.x, y - v.y, z - v.z)
    operator fun minusAssign(v: Float) {
        this.x -= v
        this.y -= v
        this.z -= z
    }

    operator fun minusAssign(v: Vec3) {
        this.x -= v.x
        this.y -= v.y
        this.z -= v.z
    }

    operator fun times(v: Float) = Vec3(x * v, y * v, z * v)
    operator fun times(v: Vec3) = Vec3(x * v.x, y * v.y, z * v.z)

    operator fun timesAssign(v: Float) {
        this.x *= v
        this.y *= v
        this.z *= v
    }

    operator fun div(v: Float) = Vec3(x / v, y / v, z / v)
    operator fun divAssign(v: Float) {
        this.x /= v
        this.y /= v
        this.z /= v
    }

    fun length(): Float {
        return sqrt(lengthSqr())
    }

    fun lengthSqr(): Float {
        return x * x + y * y + z * z
    }

    fun nearZero(): Boolean {
        val s = 1e-8f
        return this.x < s && this.y < s && this.z < 0
    }
}

fun reflect(v: Vec3, n: Vec3): Vec3 {
    return v - n * 2f * dot(v, n)
}

fun refract(uv: Vec3, n: Vec3, taiOverTat: Float): Vec3 {
    val cosTheta = min(dot(uv * -1f, n), 1f)
    val perp: Vec3 = (uv + n * cosTheta) * taiOverTat
    val parallel: Vec3 = n * -sqrt(abs(1f - perp.lengthSqr()))
    return perp + parallel
}

fun randomUnitVector(): Vec3 {
    return unitVector(randomInUnitSphere())
}

// disk is constrained to x,y
fun randomInUnitDisk(): Vec3 {
    while (true) {
        val p = Vec3(Random.nextFloat(-1f, 1f), Random.nextFloat(-1f, 1f), 0f)
        if(p.lengthSqr() >= 1)
            continue

        return p
    }
}

fun randomInUnitSphere(): Vec3 {
    while (true) {
        val p = random(-1f, 1f)
        if (p.lengthSqr() >= 1)
            continue
        return p
    }
}

fun random(): Vec3 {
    return Vec3(Random.nextFloat(), Random.nextFloat(), Random.nextFloat())
}

fun random(min: Float, max: Float): Vec3 {
    return Vec3(
        Random.nextFloat(min, max),
        Random.nextFloat(min, max),
        Random.nextFloat(min, max)
    )
}

fun dot(u: Vec3, v: Vec3): Float {
    return u.x * v.x +
            u.y * v.y +
            u.z * v.z
}

fun cross(u: Vec3, v: Vec3): Vec3 {
    return Vec3(
        u.y * v.z - u.z * v.y,
        u.z * v.x - u.x * v.z,
        u.x * v.y - u.y * v.x
    )
}

fun unitVector(v: Vec3): Vec3 {
    return v / v.length()
}

fun color3ToArgb(color: Color3): Int {
    return Color.argb(1.0f, color.r, color.g, color.b)
}

fun color3ToArgb(color: Color3, samples: Float): Int {
    val scale = 1 / samples

    val r = sqrt(clamp(color.r * scale, 0f, .999f))
    val g = sqrt(clamp(color.g * scale, 0f, .999f))
    val b = sqrt(clamp(color.b * scale, 0f, .999f))
    val colorMultiSample = Color3(r, g, b)

    //Timber.d(colorMultiSample.toString())
    return Color.argb(1.0f, colorMultiSample.r, colorMultiSample.g, colorMultiSample.b)
}

typealias   Color3 = Vec3
typealias Point3 = Vec3
