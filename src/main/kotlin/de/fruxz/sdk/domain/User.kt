package de.fruxz.sdk.domain

import de.fruxz.sdk.domain.container.Item
import de.fruxz.sdk.domain.container.UserInterface
import de.fruxz.sdk.domain.display.Transmission
import de.fruxz.sdk.util.ListUtils
import org.bukkit.entity.Player

class User(val player: Player) {

    fun sendMessage(transmission: Transmission) { transmission.sendMessage(player) }

    fun sendActionBar(transmission: Transmission) { transmission.sendActionBar(player) }

    fun openUI(ui: UserInterface) { ui.sendUser(this) }

    fun giveItem(item: Item) {
        player.inventory.addItem(item.buildLegacy())
    }

    fun giveItems(vararg items: Item) {
        player.inventory.addItem(*ListUtils().convert(items) { it.buildLegacy() })
    }

}