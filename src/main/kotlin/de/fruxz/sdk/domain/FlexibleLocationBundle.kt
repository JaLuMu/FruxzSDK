package de.fruxz.sdk.domain

import org.bukkit.Location
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.util.BoundingBox
import org.bukkit.util.Vector

/**
 * This class helps to easily create, save, manage and use 2 seperate locations and its "bounding box"
 */
class FlexibleLocationBundle : ConfigurationSerializable {

    var firstLocation: Location
    var secondLocation: Location

    constructor(firstLocation: Location, secondLocation: Location) {
        this.firstLocation = firstLocation
        this.secondLocation = secondLocation
    }

    constructor(locations: Pair<Location, Location>) {
        firstLocation = locations.first
        secondLocation = locations.second
    }

    constructor(map: Map<String, Any>) {
        firstLocation = map["first"] as Location
        secondLocation = map["second"] as Location
    }

    /**
     * Generates an [BoundingBox] of the [firstLocation] and the [secondLocation]
     * @return BoundingBox of both locations
     */
    fun generateBoundingBox() = BoundingBox.of(firstLocation, secondLocation)

    /**
     * Generates an [BoundingBox] of the [firstLocation] and the [secondLocation]
     * and returning the center of the box
     * @return Center of an [BoundingBox] of both locations
     */
    fun getCenter() = generateBoundingBox().center

    /**
     * Generates an [BoundingBox] of the [firstLocation] and the [secondLocation]
     * and returning the boolean *contains vector?*
     * @return Boolean, if the vector is inside both locations
     */
    fun contains(vector: Vector) = generateBoundingBox().contains(vector)

    /**
     * Replacing the [firstLocation] and the [secondLocation] of the
     * Object with the parameter-objects
     * @param firstLocation replacing the old [firstLocation]
     * @param secondLocation replacing the old [secondLocation]
     * @return the updated itself
     */
    fun updateLocations(firstLocation: Location, secondLocation: Location): FlexibleLocationBundle {
        updateFirstLocation(newLocation = firstLocation)
        updateSecondLocation(newLocation = secondLocation)

        return this
    }

    /**
     * Replacing the [firstLocation] and the [secondLocation] of the
     * Object with the [locations] values
     * @param locations replacing the old [firstLocation] and [secondLocation]
     * @return the updated itself
     */
    fun updateLocations(locations: Pair<Location, Location>) = updateLocations(firstLocation = locations.first, secondLocation = locations.second)

    /**
     * Replacing the [firstLocation] with the [newLocation]
     * and returning the changed object
     * @param newLocation replacing the old [firstLocation]
     * @return the updated itself
     */
    fun updateFirstLocation(newLocation: Location): FlexibleLocationBundle {

        firstLocation = newLocation

        return this
    }

    /**
     * Replacing the [secondLocation] with the [newLocation]
     * and returning the changed object
     * @param newLocation replacing the old [secondLocation]
     * @return the updated itself
     */
    fun updateSecondLocation(newLocation: Location): FlexibleLocationBundle {

        secondLocation = newLocation

        return this
    }

    /**
     * serialize for [ConfigurationSerializable]
     */
    override fun serialize() = mapOf(
        "first" to firstLocation,
        "second" to secondLocation,
    )
}