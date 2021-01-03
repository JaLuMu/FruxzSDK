package de.fruxz.sdk.domain.event.entity.player.interact

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent
import org.bukkit.inventory.ItemStack

/**
 * Fired when a player interacts with an item
 */
class PlayerInteractAtItemEvent(val whoInteract: Player, val item: ItemStack, val material: Material, private var isCancelled: Boolean = false) : PlayerInteract, PlayerEvent(whoInteract, false), Cancellable {

    override var isDenied: Boolean? = null

    override fun getHandlers() = handlerList

    override fun isCancelled() = isCancelled

    override fun setCancelled(cancel: Boolean) {
        isCancelled = cancel
    }

    companion object {
        private val handlerList = HandlerList()

        @JvmStatic
        fun getHandlerList() = handlerList

    }

}