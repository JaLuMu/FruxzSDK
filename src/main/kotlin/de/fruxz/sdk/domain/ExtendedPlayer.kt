package de.fruxz.sdk.domain

import de.fruxz.sdk.domain.display.Transmission
import org.bukkit.entity.Player

class ExtendedPlayer(val player: Player) {

    fun sendMessage(transmission: Transmission) {
        transmission.sendMessage(player)
    }

    fun sendActionBar(transmission: Transmission) {
        transmission.sendActionBar(player)
    }

}