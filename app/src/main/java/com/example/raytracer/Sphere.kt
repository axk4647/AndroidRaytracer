package com.example.raytracer

import kotlin.math.sqrt

class Sphere(val center: Point3, val radius: Float, val material: Material) : Hittable {

    override fun hit(r: Ray, tMin: Float, tMax: Float): HitRecord? {
        val oc = r.origin - center
        val a = r.direction.lengthSqr()
        val halfB = dot(oc, r.direction)
        val c = oc.lengthSqr() - radius * radius
        val discriminant = halfB * halfB - a * c

        if (discriminant < 0)
            return null

        val sqrtd = sqrt(discriminant)

        var root = (-halfB - sqrtd) / a
        if (root < tMin || root > tMax) {
            root = (-halfB + sqrtd) / a
            if (root < tMin || root > tMax) {
                return null
            }
        }

        val t = root
        val p = r.at(root)
        val outwardNormal: Vec3  = (p - center) / radius

        val rec = HitRecord(p, outwardNormal, t, material)

        rec.setFaceNormal(r, outwardNormal)
        return rec
    }
}