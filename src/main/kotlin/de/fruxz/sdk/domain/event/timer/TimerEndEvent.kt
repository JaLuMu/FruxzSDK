package de.fruxz.sdk.domain.event.timer

import de.fruxz.sdk.domain.timer.Timer
import de.fruxz.sdk.kernel.FruxzPlugin
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class TimerEndEvent(override val owningPlugin: FruxzPlugin, override val timer: Timer, isAsync: Boolean = true) : TimerEvent, Event(isAsync) {

    override fun getHandlers() = handlerList

    var endFunction = timer.onEnd

    companion object {

        @JvmStatic
        private val handlerList = HandlerList()

        @JvmStatic
        fun getHandlerList() = handlerList

    }

}