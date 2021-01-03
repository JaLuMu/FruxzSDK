package de.fruxz.sdk.domain.event.entity.player.interact

import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

/**
 * Fired when a player interacts with an block
 */
class PlayerInteractAtBlockEvent(val whoInteract: Player, val block: Block, val blockFace: BlockFace, val material: Material, private var isCancelled: Boolean = false) : PlayerInteract, PlayerEvent(whoInteract, false), Cancellable {

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