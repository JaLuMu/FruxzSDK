package de.fruxz.sdk.domain.container

import de.fruxz.sdk.domain.User
import de.fruxz.sdk.domain.display.TransmissionContentObjectable
import de.fruxz.sdk.util.ListUtils
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.jetbrains.annotations.NotNull

/**
 * This class helps to easily manage multiple [Item]s at once
 */
class ItemBundle : ConfigurationSerializable, TransmissionContentObjectable {

    /**
     * The cache of used items
     */
    val items: ArrayList<Item>

    constructor() {
        items = ArrayList()
    }

    constructor(vararg items: Item) {
        this.items = ArrayList(items.toList())
    }

    constructor(items: Collection<Item>) {
        this.items = ArrayList(items)
    }

    constructor(vararg items: ItemStack) {
        this.items = ListUtils().convert(items.toList()) { Item(it) }
    }

    constructor(vararg materials: Material) {
        this.items = ListUtils().convert(materials.toList()) { Item(it) }
    }

    constructor(map: Map<String, Any>) {
        this.items = ArrayList(map["items"] as List<Item>)
    }

    /**
     * Adding an Item to the cache
     */
    fun addItem(item: Item) = items.add(item)

    /**
     * Adding an Item to the cache
     */
    fun addItem(material: Material) = addItem(Item(material))

    /**
     * Removing an or all item(s) from the cache
     */
    fun takeItem(item: Item, onlyFirst: Boolean = false) {

        if (onlyFirst) {
            items.remove(item)
        } else {
            val out = ArrayList<Item>(items.clone() as Collection<Item>)

            out.filter { it.isSame(item) }

            if (out.size > 0) {
                repeat((0..out.size).count()) {
                    items.remove(item)
                }
            }

        }

    }

    /**
     * Removing an or all item(s) from the cache
     */
    fun takeItem(material: Material, onlyFirst: Boolean = false) {
        val input = ArrayList<Item>(items.clone() as Collection<Item>)
        val output = ArrayList<Item>()

        for (item in input) {
            if (item.material != material) {
                output.add(item)
            }

            if (onlyFirst)
                break
        }
    }

    /**
     * Checks, if an item equals [item] is inside the cache
     */
    fun hasItem(item: Item) = items.any { it.isSame(item) }

    /**
     * Checks, if an item with the type of [material] is inside the cache
     */
    fun hasItem(material: Material) = items.any { it.material == material }

    /**
     * Giving all items from the cache the user [user]
     */
    fun giveItems(user: User) = user.giveItems(*items.toTypedArray())

    /**
     * Giving all items from the cache the player [player]
     */
    fun giveItems(player: Player) = giveItems(User(player))

    /**
     * Returning an list of all items as legacy-items
     */
    fun legacyItems() = ListUtils().convert(items) { it.buildLegacy() }

    /**
     * Building an TextComponent for the display of the stack
     */
    @NotNull
    fun buildDisplayObject(bracketsColor: ChatColor = ChatColor.GRAY, nameColor: ChatColor? = null): TextComponent {
        val out = TextComponent("$bracketsColor[${nameColor?.toString() ?: ""}Item-Bundle$bracketsColor]")
        var message = ""

        items.forEach {
            message += "§7${it.size}x ${it.label}\n"
        }

        out.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, net.md_5.bungee.api.chat.hover.content.Text(message))

        return out
    }

    override fun serialize() = mapOf(
        "items" to items
    )

    override fun getObjectable() = buildDisplayObject()

}