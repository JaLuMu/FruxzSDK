package de.fruxz.sdk.util

import org.bukkit.Location
import org.bukkit.util.BoundingBox

/**
 * This class helps to easily manage Ranges
 */
class RangeUtils {

    /**
     * Checks, if an int [checking] is between [x1] and [x2] or is one of the values
     */
    fun isInRange(x1: Int, x2: Int, checking: Int) = (x1..x2).contains(checking)

    /**
     * Checks, if an int [checking] is between [x1] and [x2] or is one of the values
     */
    fun isInRange(x1: Location, x2: Location, checking: Location) = BoundingBox.of(x1, x2).contains(checking.toVector())

}