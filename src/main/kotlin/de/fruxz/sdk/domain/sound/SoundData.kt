package de.fruxz.sdk.domain.sound

import org.bukkit.Sound
import org.bukkit.configuration.serialization.ConfigurationSerializable

/**
 * This class helps to easily manage sound and its properties
 */
class SoundData : ConfigurationSerializable, Cloneable {

    var type: Sound
    var volume: Double
    var pitch: Double

    constructor(type: Sound, volume: Double, pitch: Double) {
        this.type = type
        this.volume = volume
        this.pitch = pitch
    }

    constructor(map: Map<String, Any>) {
        type = Sound.valueOf("" + map["type"])
        volume = (map["volume"] as Number).toDouble()
        pitch = (map["pitch"] as Number).toDouble()
    }

    override fun serialize() = mapOf(
        "type" to type.name,
        "volume" to volume,
        "pitch" to pitch,
    )

    override fun clone() = SoundData(type, volume, pitch)

    fun builder() = SoundBuilder(this)

}