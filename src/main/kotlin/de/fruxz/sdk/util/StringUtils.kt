package de.fruxz.sdk.util

/**
 * This class helps to easily manage strings and its behavior
 */
class StringUtils {

    /**
     * Checks, if the String ([s]) is an valid Int [Int]
     */
    fun isInt(s: String): Boolean {
        return try {
            s.toInt()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    /**
     * Checks, if the String ([s]) is an valid String [String]
     */
    fun isBoolean(s: String) = s.equals("true", true) || s.equals("false", true)

    /**
     * Creates an string with an border around it, filled with [border] and [size] wide.
     */
    fun centerString(s: String, border: Char, size: Int) = s.padStart(s.length+size, border).padEnd(s.length+(size*2), border)

}