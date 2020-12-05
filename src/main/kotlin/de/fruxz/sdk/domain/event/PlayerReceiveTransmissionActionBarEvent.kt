package de.fruxz.sdk.domain.event

import de.fruxz.sdk.domain.display.Transmission
import de.fruxz.sdk.kernel.FruxzPlugin
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

class PlayerReceiveTransmissionActionBarEvent(val plugin: FruxzPlugin, var receiver: Player, var transmission: Transmission, private var isCancelled: Boolean): PlayerEvent(receiver, false), Cancellable {

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