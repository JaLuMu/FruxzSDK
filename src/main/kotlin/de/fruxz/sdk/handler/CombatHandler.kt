package de.fruxz.sdk.handler

import de.fruxz.sdk.domain.event.entity.player.PlayerDamageByPlayerEvent
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

class CombatHandler : Listener {

    @EventHandler
    fun onEntityDamageByEntity(event: EntityDamageByEntityEvent) {

        if (event.entity is Player && event.damager is Player) {
            val fruxzEvent = PlayerDamageByPlayerEvent(event.entity as Player, event.damager as Player)

            if (fruxzEvent.callEvent()) {
                event.isCancelled = fruxzEvent.isCancelled
            }

        }

    }

}