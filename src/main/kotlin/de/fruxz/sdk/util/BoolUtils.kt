package de.fruxz.sdk.util

class BoolUtils {

    fun <T> boolSelector(boolean: Boolean, yes: T, no: T): T {
        return if (boolean) yes else no
    }

}