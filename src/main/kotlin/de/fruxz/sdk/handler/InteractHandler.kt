package de.fruxz.sdk.handler

import de.fruxz.sdk.domain.event.entity.player.interact.PlayerInteractAtBlockEvent
import de.fruxz.sdk.domain.event.entity.player.interact.PlayerInteractAtItemEvent
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class InteractHandler : Listener {

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {

        if (event.hasItem()) {
            val fruxzEvent = PlayerInteractAtItemEvent(event.player, event.item!!, event.material)

            if (fruxzEvent.callEvent()) {
                event.isCancelled = fruxzEvent.isCancelled
                event.setUseItemInHand(when (fruxzEvent.isDenied) {

                    true -> Event.Result.DENY

                    false -> Event.Result.ALLOW

                    null -> Event.Result.DEFAULT

                })
            }
        }

        if (event.hasBlock()) {
            val fruxzEvent = PlayerInteractAtBlockEvent(event.player, event.clickedBlock!!, event.blockFace, event.material)

            if (fruxzEvent.callEvent()) {
                event.isCancelled = fruxzEvent.isCancelled
                event.setUseInteractedBlock(when (fruxzEvent.isDenied) {

                    true -> Event.Result.DENY

                    false -> Event.Result.ALLOW

                    null -> Event.Result.DEFAULT

                })
            }
        }

    }

}