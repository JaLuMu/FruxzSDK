package de.fruxz.sdk.domain.timer

import de.fruxz.sdk.Main
import de.fruxz.sdk.domain.event.timer.TimerStartEvent
import de.fruxz.sdk.domain.tasks.Tasky
import de.fruxz.sdk.kernel.FruxzPlugin
import java.util.*

class TimerManager : Tasky {

    companion object {
        var cache = emptyList<Timer>()
    }

    fun runTimer(newTimer: Timer, providingPlugin: FruxzPlugin = newTimer.owningPlugin) {
        if (!cache.any { it.uniqueIdentity == newTimer.uniqueIdentity }) {

            if (newTimer.remainingSeconds > newTimer.delayingSeconds) {

                if (newTimer.delayingSeconds > 0) {

                    cache = cache + newTimer

                    async(Main.instance) {
                        providingPlugin.callEvent(TimerStartEvent(newTimer.owningPlugin, newTimer))
                    }

                } else
                    throw IllegalArgumentException("delayingSeconds must be greater than 0!")

            } else
                throw IllegalArgumentException("remainingSeconds must be greater than delayingSeconds!")

        } else
            throw IllegalArgumentException("newTimer is already running!")
    }

    fun getCurrentTimerStatus(uniqueIdentity: UUID) = cache.first { it.uniqueIdentity == uniqueIdentity }

}