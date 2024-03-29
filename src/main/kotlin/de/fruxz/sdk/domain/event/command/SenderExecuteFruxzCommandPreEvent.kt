package de.fruxz.sdk.domain.event.command

import de.fruxz.sdk.kernel.Command
import de.fruxz.sdk.kernel.FruxzPlugin
import org.bukkit.command.CommandSender
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class SenderExecuteFruxzCommandPreEvent(val executedPlugin: FruxzPlugin, val sender: CommandSender, val command: Command, private var isCancelled: Boolean = false, label: String, parameters: Array<out String>) : Event(false), Cancellable {

    override fun getHandlers() = generatedHandlerList

    companion object {

        @JvmStatic
        private val generatedHandlerList = HandlerList()

        @JvmStatic
        fun getHandlerList() = generatedHandlerList

    }

    override fun isCancelled() = isCancelled

    override fun setCancelled(cancel: Boolean) { isCancelled = cancel }

}