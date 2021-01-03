package de.fruxz.sdk.domain.event.entity.player

import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

/**
 * Fired when a player is damaged by an player
 */
class PlayerDamageByPlayerEvent(val attacked: Player, val attacker: Player, private var isCancelled: Boolean = false) : PlayerEvent(attacked, false), Cancellable {

    override fun getHandlers() = handlerList

    companion object {
        private val handlerList = HandlerList()

        @JvmStatic
        fun getHandlerList() = handlerList

    }

    override fun isCancelled() = isCancelled

    override fun setCancelled(cancel: Boolean) {
        isCancelled = cancel
    }

}