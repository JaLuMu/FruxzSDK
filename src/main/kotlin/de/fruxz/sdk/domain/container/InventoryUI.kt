package de.fruxz.sdk.domain.container

import de.fruxz.sdk.domain.User
import de.fruxz.sdk.util.ListUtils
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

class InventoryUI : ConfigurationSerializable, UserInterface, Cloneable {

    var contents: HashMap<Int, Item>
    var label: String
    var size: InventorySize

    constructor(label: String = "container", size: InventorySize = InventorySize.SMALL, contents: Map<Int, Item> = emptyMap()) {
        this.contents = HashMap(contents)
        this.label = label
        this.size = size
    }

    constructor(map: Map<String, Any>) {

        this.label = "${map["label"]}"
        this.size = InventorySize.valueOf("${map["size"]}")
        this.contents = HashMap(map["content"] as Map<Int, Item>)

    }

    fun replace(that: Material, withThat: Material) {
        val out = HashMap<Int, Item>()

        contents.forEach { (key, value) ->
            if (value.material != that) {

                val clone = value.clone()

                 clone.material = withThat

                out[key] = value

            } else
                out[key] = value
        }

        contents = out
    }

    fun replace(that: Item, withThat: Item) {
        val out = HashMap<Int, Item>()

        contents.forEach { (key, value) ->
            out[key] = if (value.isSame(that)) withThat else value
        }

        contents = out
    }

    fun replace(that: Material, withThat: Item) {
        val out = HashMap<Int, Item>()

        contents.forEach { (key, value) ->
            out[key] = if (value.isSame(
                    other = Item(that),
                    ignoreLabel = true,
                    ignoreSize = true,
                    ignoreDamage = true,
                    ignoreLore = true,
                    ignoreModifications = true,
                    ignoreMaterial = false
                )) withThat else value
        }

        contents = out
    }

    fun replace(that: Item, withThat: Material) {
        val out = HashMap<Int, Item>()

        contents.forEach { (key, value) ->
            out[key] = if (that.isSame(
                    other = Item(withThat),
                    ignoreLabel = true,
                    ignoreSize = true,
                    ignoreDamage = true,
                    ignoreLore = true,
                    ignoreModifications = true,
                    ignoreMaterial = false
                )) Item(withThat) else value
        }

        contents = out
    }

    fun background(item: Item) = replace(Material.AIR, item)

    fun background(material: Material) = background(Item(material))

    fun fill(vararg items: Item) {
        (0..contents.size).forEach { slot ->
            contents[slot] = items.random()
        }
    }

    fun fill(vararg materials: Material) = fill(*ListUtils().convert(materials) { Item(it) })

    fun border(item: Item, schema: Array<Int>) {
        schema.forEach { position ->
            contents[position] = item
        }
    }

    fun border(material: Material, schema: Array<Int>) = border(Item(material), schema)

    fun border(item: Item, border: UIBorder) = border(item, border.getItemPositions(size))

    fun border(material: Material, border: UIBorder) = border(Item(material), border.getItemPositions(size))

    fun buildInventory(): Inventory {
        val inventory = Bukkit.createInventory(null, size.size, label)

        contents.forEach { (key, value) ->
            inventory.setItem(key, value.buildLegacy())
        }

        return inventory
    }

    override fun serialize() = mapOf(
        "label" to label,
        "size" to size,
        "content" to contents
    )

    override fun sendUser(user: Player) {
        user.openInventory(buildInventory())
    }

    override fun sendUser(user: User) = sendUser(user.player)

    public override fun clone(): InventoryUI {
        return InventoryUI(label, size, contents)
    }

}