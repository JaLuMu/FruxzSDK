package de.fruxz.sdk.domain.sound

import de.fruxz.sdk.Main
import de.fruxz.sdk.kernel.FruxzPlugin
import org.bukkit.Location
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class SoundMelody : ConfigurationSerializable, Cloneable {

    /**
     * Providing plugin
     */
    val plugin: FruxzPlugin

    /**
     * How oft the melody repeats?
     * ***0: never | -1 infinite | >0 times***
     */
    var repeats: Int

    /**
     * The sounds playing in the melody
     */
    var melodyContent: ArrayList<Array<SoundData>>

    /**
     * Ticks between one and other melody section
     */
    var time: Int

    constructor(plugin: FruxzPlugin, repeats: Int = 0, time: Int, melodyContent: ArrayList<Array<SoundData>>) {
        this.plugin = plugin
        this.repeats = repeats
        this.time = time
        this.melodyContent = melodyContent
    }

    constructor(map: Map<String, Any>) {
        plugin = Main.instance
        repeats = (map["repeates"] as Number).toInt()
        time = (map["time"] as Number).toInt()
        melodyContent = ArrayList((map["melodyContent"] as ArrayList<Array<SoundData>>?) ?: emptyList())
    }

    private var allowedToRun = false
    private var paused = false

    /**
     * Cancels the playback
     */
    fun stop() {
        allowedToRun = false
    }

    /**
     * Pauses or revive the playback
     */
    fun hold(value: Boolean) {
        paused = value
    }

    /**
     * Pauses/revive or get playstatus of the playback
     */
    var isPaused: Boolean
        get() = paused
        set(value) { hold(value) }

    /**
     * Play the playback to everyone at one location
     */
    fun play(where: Location) {
        allowedToRun = true

        if (!melodyContent.isNullOrEmpty()) {
            object : BukkitRunnable() {

                var repeated = 0
                var innerRound = 0

                override fun run() {
                    if (!paused) {
                        val currentSounds = melodyContent[innerRound]

                        currentSounds.forEach {
                            it.builder().playSound(where = where)
                        }

                        innerRound++
                        if (innerRound >= melodyContent.size) {
                            innerRound = 0
                            if (allowedToRun && (repeats == -1 || repeats > repeated)) {
                                repeated++
                            } else {
                                allowedToRun = false
                                cancel()
                            }
                        }
                    }
                }

            }.runTaskTimerAsynchronously(plugin, 0, time.toLong())
        }

    }

    /**
     * Play the playback to only certain players at one position
     */
    fun play(where: Location, vararg receivers: Player) {
        allowedToRun = true

        if (!melodyContent.isNullOrEmpty()) {
            object : BukkitRunnable() {

                var repeated = 0
                var innerRound = 0

                override fun run() {
                    if (!paused) {
                        val currentSounds = melodyContent[innerRound]

                        currentSounds.forEach {
                            it.builder().playSound(where = where, receivers = receivers)
                        }

                        innerRound++
                        if (innerRound >= melodyContent.size) {
                            innerRound = 0
                            if (allowedToRun && (repeats == -1 || repeats > repeated)) {
                                repeated++
                            } else {
                                allowedToRun = false
                                cancel()
                            }
                        }
                    }
                }

            }.runTaskTimerAsynchronously(plugin, 0, time.toLong())
        }

    }

    /**
     * Play the playback to only certain players everywhere
     */
    fun play(vararg receivers: Player) {
        allowedToRun = true

        if (!melodyContent.isNullOrEmpty()) {
            object : BukkitRunnable() {

                var repeated = 0
                var innerRound = 0

                override fun run() {
                    if (!paused) {
                        val currentSounds = melodyContent[innerRound]

                        currentSounds.forEach {
                            it.builder().playSound(receivers = receivers)
                        }

                        innerRound++
                        if (innerRound >= melodyContent.size) {
                            innerRound = 0
                            if (allowedToRun && (repeats == -1 || repeats > repeated)) {
                                repeated++
                            } else {
                                allowedToRun = false
                                cancel()
                            }
                        }
                    }
                }

            }.runTaskTimerAsynchronously(plugin, 0, time.toLong())
        }

    }

    override fun serialize() = mapOf(
        "repeats" to repeats,
        "time" to time,
        "melodyContent" to melodyContent
    )

    public override fun clone() = SoundMelody(plugin, repeats, time, melodyContent)

}