package de.fruxz.sdk.domain.event.world

import de.fruxz.sdk.domain.WeatherState
import org.bukkit.World
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class WeatherStateUpdateEvent(val world: World, val newState: WeatherState, private var isCancelled: Boolean) : Event(false), Cancellable {

    override fun getHandlers() = handlerList

    companion object {

        @JvmStatic
        private val handlerList = HandlerList()

        @JvmStatic
        fun getHandlerList() = handlerList

    }

    override fun isCancelled() = isCancelled

    override fun setCancelled(cancel: Boolean) {
        isCancelled = cancel
    }

}