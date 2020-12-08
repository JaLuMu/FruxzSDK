package de.fruxz.sdk.util

/**
 * This class helps to easily manage booleans
 */
class BoolUtils {

    /**
     * This return different values defined by the positive or negative value
     * of the boolean [boolean]
     */
    fun <T> boolSelector(boolean: Boolean, yes: T, no: T): T {
        return if (boolean) yes else no
    }

}