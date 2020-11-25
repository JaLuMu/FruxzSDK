package de.fruxz.sdk.domain

import de.fruxz.sdk.domain.container.Item
import de.fruxz.sdk.domain.container.ItemBundle
import de.fruxz.sdk.domain.container.UserInterface
import de.fruxz.sdk.domain.display.Transmission
import de.fruxz.sdk.util.ListUtils
import org.bukkit.entity.Player

/**
 * This class is an assistive class for the [Player] interface
 */
class User(val player: Player) {

    /**
     * Send a message to the [User] using an Transmission
     */
    fun sendMessage(transmission: Transmission) { transmission.sendMessage(player) }

    /**
     * Send a actionbar-message to the [User] using an Transmission
     */
    fun sendActionBar(transmission: Transmission) { transmission.sendActionBar(player) }

    /**
     * Send a [UserInterface] to the [User]
     */
    fun openUI(ui: UserInterface) { ui.sendUser(this) }

    /**
     * Give the [User] the custom-item [item]
     */
    fun giveItem(item: Item) {
        player.inventory.addItem(item.buildLegacy())
    }

    /**
     * Give the [User] the custom-items [items]
     */
    fun giveItems(vararg items: Item) {
        player.inventory.addItem(*ListUtils().convert(items) { it.buildLegacy() })
    }

    fun giveItems(bundle: ItemBundle) = bundle.giveItems(this)

    fun setItem(slot: Int, item: Item) {
        player.inventory.setItem(slot, item.buildLegacy())
    }

}