package de.fruxz.sdk.util

class StringUtils {

    fun isInt(s: String): Boolean {
        return try {
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    fun isBoolean(s: String) = s.equals("true", true) || s.equals("false", true)

    fun centerString(s: String, border: Char, size: Int) = s.padStart(s.length+size, border).padEnd(s.length+(size*2), border)

}