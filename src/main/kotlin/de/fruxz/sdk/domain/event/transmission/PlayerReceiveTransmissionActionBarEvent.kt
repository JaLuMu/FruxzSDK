package de.fruxz.sdk.domain.event.transmission

import de.fruxz.sdk.domain.display.Transmission
import de.fruxz.sdk.kernel.FruxzPlugin
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

class PlayerReceiveTransmissionActionBarEvent(override val plugin: FruxzPlugin, var receiver: Player, override var transmission: Transmission, private var isCancelled: Boolean): PlayerEvent(receiver, false), TransmissionEvent {

    override fun isCancelled() = isCancelled

    override fun setCancelled(cancel: Boolean) {
        isCancelled = cancel
    }

    override fun getHandlers() = generatedHandlerList

    companion object {

        @JvmStatic
        private val generatedHandlerList = HandlerList()

        @JvmStatic
        fun getHandlerList() = generatedHandlerList
    }

}