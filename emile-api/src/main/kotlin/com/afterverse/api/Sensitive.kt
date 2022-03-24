package com.afterverse.api

class Sensitive<T>(val value: T, val clazz: Class<T>) {
    override fun toString() = "REDACTED"
    override fun hashCode() = value.hashCode()
    override fun equals(other: Any?) = value == (other as? Sensitive<*>)?.value
}

inline fun <reified T> T.toSensitive() = Sensitive(this, T::class.java)
fun <T> T.toSensitive(clazz: Class<T>) = Sensitive(this, clazz)
