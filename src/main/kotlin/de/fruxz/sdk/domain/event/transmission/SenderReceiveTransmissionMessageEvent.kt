package de.fruxz.sdk.domain.event.transmission

import de.fruxz.sdk.domain.display.Transmission
import de.fruxz.sdk.kernel.FruxzPlugin
import org.bukkit.command.CommandSender
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class SenderReceiveTransmissionMessageEvent(override val plugin: FruxzPlugin, var receiver: CommandSender, override var transmission: Transmission, private var isCancelled: Boolean): Event(false), TransmissionEvent {

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