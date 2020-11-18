package de.fruxz.sdk.util

import org.bukkit.Location
import org.bukkit.util.BoundingBox

class RangeUtils {

    fun isInRange(x1: Int, x2: Int, checking: Int) = (x1..x2).contains(checking)

    fun isInRange(x1: Location, x2: Location, checking: Location) = BoundingBox.of(x1, x2).contains(checking.toVector())

}