package de.fruxz.sdk.domain.container

import de.fruxz.sdk.domain.User
import de.fruxz.sdk.util.ListUtils
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack

/**
 * This class helps to create, design and construct inventories in a simple, structured and complex way at the same time.
 * Also, with this construct, this class helps to create everything as a [UserInterface] and keep it writable and
 * readable for the configuration files so that ConfigurationSerialization is possible.
 *
 * WIKI: [Object: InventoryUI](https://github.com/TheFruxz/FruxzSDK/wiki/Object:-InventoryUI)
 */
class InventoryUI : ConfigurationSerializable, UserInterface, Cloneable {

    /**
     * This HashMap stores the data of the contents of the inventory using the slot positions [Int] and their [Item].
     */
    var contents: HashMap<Int, Item>

    /**
     * Specifies the display name of the inventory, which is needed when dispensing and tapping.
     */
    var label: String

    /**
     * Specifies the size of the inventory using an [InventorySize] enum object.
     */
    var size: InventorySize

    /**
     * Generates an [InventoryUI] using the required parameters.
     * @param label defines the display name of the inventory
     * @param size defines the size of the inventory
     * @param contents defines the contents of the inventory
     */
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

    /**
     * Does replace any content from the [contents] with a new [Item] based on [withThat] which has the [Material]/type [that].
     */
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

    /**
     * Does replace any content from the [contents] with a [withThat] which is [that].
     */
    fun replace(that: Item, withThat: Item) {
        val out = HashMap<Int, Item>()

        contents.forEach { (key, value) ->
            out[key] = if (value.isSame(that)) withThat else value
        }

        contents = out
    }

    /**
     * Does replace any content from the [contents] with a [withThat] which has the [Material]/type [that].
     */
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

    /**
     * Does replace any content from the [contents] with a new [Item] based on [withThat] which is [that].
     */
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

    /**
     * Replaces any air ([Material.AIR]) with an [item] to fill the free background with this item ([item]).
     */
    fun background(item: Item) = replace(Material.AIR, item)

    /**
     * Replaces any air ([Material.AIR]) with an new [Item] based on [material] to fill the free background with this item.
     */
    fun background(material: Material) = background(Item(material))

    /**
     * Fills up the inventory with [items] and thus replaces all [Item]s already inserted.
     * If multiple [Item]s are specified within [items], the contents are randomly selected.
     */
    fun fill(vararg items: Item) {
        (0..size.size).forEach { slot ->
            contents[slot] = items.random()
        }
    }

    /**
     * Fills up the inventory with [Item]s based on [materials] and thus replaces all [Item]s already inserted.
     * If multiple [Material]s are specified within [materials], the contents are randomly selected.
     */
    fun fill(vararg materials: Material) = fill(*ListUtils().convert(materials) { Item(it) })

    /**
     * Creates an inventory border, which was previously specified with a writing scheme [schema].
     * All item slots specified in [schema] are overwritten with [item].
     */
    fun border(item: Item, schema: Array<Int>) {
        schema.forEach { position ->
            contents[position] = item
        }
    }

    /**
     * Creates an inventory border, which was previously specified with a writing scheme [schema].
     * All item slots specified in [schema] are overwritten with an new [Item] based on [material].
     */
    fun border(material: Material, schema: Array<Int>) = border(Item(material), schema)

    /**
     * Creates an inventory border, which was previously specified with a writing scheme [border].
     * All item slots specified in [border] are overwritten with [item].
     */
    fun border(item: Item, border: UIBorder) = border(item, border.getItemPositions(size))

    /**
     * Creates an inventory border, which was previously specified with a writing scheme [border].
     * All item slots specified in [border] are overwritten with an new [Item] based on [material].
     */
    fun border(material: Material, border: UIBorder) = border(Item(material), border.getItemPositions(size))

    /**
     * Generates a Bukkit [Inventory] from the data stored in the [InventoryUI] object,
     * which can be used for Bukkit/Spigot/PaperMC purposes.
     */
    fun buildInventory(): Inventory {
        val inventory = Bukkit.createInventory(null, size.size, label)

        contents.forEach { (key, value) ->
            inventory.setItem(key, value.buildLegacy())
        }

        return inventory
    }

    /**
     * Sets [item] to the [slot] within the [contents].
     */
    fun place(slot: Int, item: Item) {
        contents[slot] = item
    }

    /**
     * Sets an Item based on [stack] to the [slot] within the [contents].
     */
    fun place(slot: Int, stack: ItemStack) = place(slot = slot, item = Item(stack))

    /**
     * Sets an Item based on [material] to the [slot] within the [contents].
     */
    fun place(slot: Int, material: Material) = place(slot = slot, item = Item(material))

    /**
     * Sets [bundle]'s [bundleSlot] to the [slot] within the [contents].
     */
    fun place(slot: Int, bundle: ItemBundle, bundleSlot: Int) = place(slot = slot, item = bundle.items[bundleSlot])

    override fun serialize() = mapOf(
        "label" to label,
        "size" to size,
        "content" to contents
    )

    /**
     * Opens the inventory built with [buildInventory] to [user].
     */
    override fun sendUser(user: Player) {
        user.openInventory(buildInventory())
    }

    /**
     * Opens the inventory built with [buildInventory] to [user].
     */
    override fun sendUser(user: User) = sendUser(user.player)

    public override fun clone(): InventoryUI {
        return InventoryUI(label, size, contents)
    }

}