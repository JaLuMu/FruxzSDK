package de.fruxz.sdk.domain.event.entity.player.interact

import de.fruxz.sdk.domain.container.Item
import de.fruxz.sdk.domain.container.ItemBundle
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent
import org.bukkit.inventory.ItemStack

/**
 * Fired when a player interacts with an item
 */
class PlayerInteractAtItemEvent(val whoInteract: Player, val itemStack: ItemStack, val material: Material, private var isCancelled: Boolean = false) : PlayerInteract, PlayerEvent(whoInteract, false), Cancellable {

    override var isDenied: Boolean? = null

    override fun getHandlers() = handlerList

    override fun isCancelled() = isCancelled

    override fun setCancelled(cancel: Boolean) {
        isCancelled = cancel
    }

    val asItem: Item
        get() = Item(itemStack)

    val asBundle: ItemBundle
        get() = ItemBundle(asItem)

    companion object {
        private val handlerList = HandlerList()

        @JvmStatic
        fun getHandlerList() = handlerList

    }

}