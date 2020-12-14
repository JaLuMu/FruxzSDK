package de.fruxz.sdk.domain.sound

import com.sun.istack.internal.Builder
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.entity.Player

/**
 * This class helps to easily create, manage and play minecraft-sounds
 */
class SoundBuilder : Cloneable, Builder<SoundData> {

    private var data: SoundData

    constructor(soundData: SoundData) {
        data = soundData
    }

    constructor(type: Sound, volume: Double = 1.0, pitch: Double = 1.0) {
        data = SoundData(type = type, volume = volume, pitch = pitch)
    }

    constructor(type: Sound, volume: Float = 1F, pitch: Float = 1F) {
        data = SoundData(type = type, volume = volume.toDouble(), pitch = pitch.toDouble())
    }

    constructor(type: Sound, volume: Int = 1, pitch: Int = 1) {
        data = SoundData(type = type, volume = volume.toDouble(), pitch = pitch.toDouble())
    }

    fun playSound(where: Location) {
        where.world.playSound(where, data.type, data.volume.toFloat(), data.pitch.toFloat())
    }

    fun playSound(where: Location, vararg receivers: Player) = receivers.forEach {
        it.playSound(where, data.type, data.volume.toFloat(), data.pitch.toFloat())
    }

    fun playSound(vararg receivers: Player) = receivers.forEach {
        it.playSound(it.location, data.type, data.volume.toFloat(), data.pitch.toFloat())
    }

    fun create() = data

    override fun build() = create()

    public override fun clone() = SoundBuilder(data)

}