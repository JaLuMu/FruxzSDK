package de.fruxz.sdk.domain.container

import de.fruxz.sdk.util.ListUtils
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import org.bukkit.inventory.meta.ItemMeta
import org.jetbrains.annotations.NotNull

class Item : Cloneable, ConfigurationSerializable {

    var material: Material
    var size: Int
    var damage: Int
    var label: String
    var lore: ItemLore
    var modifications: ArrayList<EnchantmentData>

    constructor() {
        material = Material.AIR
        label = ChatColor.WHITE.toString() + material.name
        size = 1
        damage = 0
        lore = ItemLore()
        modifications = ArrayList()
    }

    constructor(material: Material) {
        this.material = material
        label = ChatColor.WHITE.toString() + material.name
        size = 1
        damage = 0
        lore = ItemLore()
        modifications = ArrayList()
    }

    constructor(itemStack: ItemStack) {
        label = if (itemStack.itemMeta.hasDisplayName()) itemStack.itemMeta.displayName else ChatColor.WHITE.toString() + itemStack.type.name
        material = itemStack.type
        size = itemStack.amount
        damage = if (itemStack.itemMeta is Damageable) { (itemStack.itemMeta as Damageable).damage } else 0
        lore = ItemLore(itemStack.lore)
        modifications = ArrayList(legacyEnchantmentsToData(itemStack.enchantments))
    }

    constructor(material: Material, label: String = material.name, size: Int = 1, damage: Int = 0, lore: ItemLore = ItemLore(), modifications: List<EnchantmentData> = emptyList()) {
        this.material = material
        this.size = size
        this.label = label
        this.damage = damage
        this.lore = lore
        this.modifications = ArrayList(modifications)
    }

    constructor(map: Map<String, Any>) {
        material = Material.matchMaterial("" + map["material"])!!
        label = "" + map["label"]
        size = (map["size"] as Number).toInt()
        damage = (map["damage"] as Number).toInt()
        lore = map["lore"] as ItemLore
        modifications = ArrayList(map["modifications"] as List<EnchantmentData>)
    }

    var activeLore: List<String>
        get() = lore.content
        set(value) { lore = ItemLore(value) }

    fun buildLegacy(forceEnchantments: Boolean = true, hideEnchantments: Boolean = false): ItemStack {
        val itemStack = ItemStack(material, size, damage.toShort())
        val itemMeta = buildMeta()

        enchantmentsDataToLegacy(modifications).forEach { (key, value) ->
            itemMeta.addEnchant(key, value, forceEnchantments)
        }

        if (hideEnchantments)
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        itemStack.itemMeta = itemMeta

        return itemStack
    }

    fun buildMeta(): ItemMeta {
        val itemMeta = Bukkit.getItemFactory().getItemMeta(material)

        itemMeta.setDisplayName(label)
        itemMeta.lore = lore.content

        return itemMeta
    }

    /**
     * Building an new [ItemBundle] with this [Item]-Object an may
     * additional objects specified in [moreItems]
     * @param moreItems additional objects in the [ItemBundle]
     */
    fun buildBundle(vararg moreItems: Item) = ItemBundle(listOf(this, *moreItems))

    /**
     * Building an new [ItemBundle] with this [Item]-Object an may
     * additional objects specified in [moreMaterials]
     * @param moreMaterials additional objects in the [ItemBundle]
     */
    fun buildBundle(vararg moreMaterials: Material) = buildBundle(moreItems = ListUtils().convert(moreMaterials) { Item(it) })

    /**
     * Building an new [ItemBundle] with this [Item]-Object an may
     * additional objects specified in [moreStacks]
     * @param moreStacks additional objects in the [ItemBundle]
     */
    fun buildBundle(vararg moreStacks: ItemStack) = buildBundle(moreItems = ListUtils().convert(moreStacks) { Item(it) })

    fun isSame(
        other: Item,
        ignoreMaterial: Boolean = false,
        ignoreLabel: Boolean = false,
        ignoreSize: Boolean = false,
        ignoreDamage: Boolean = false,
        ignoreLore: Boolean = false,
        ignoreModifications: Boolean = false

    ): Boolean {
        var isOtherItem = false

        if (!ignoreMaterial) {
            if (this.material != other.material) {
                isOtherItem = true
            }
        }

        if (!ignoreLabel) {
            if (this.label != other.label) {
                isOtherItem = true
            }
        }

        if (!ignoreSize) {
            if (this.size != other.size) {
                isOtherItem = true
            }
        }

        if (!ignoreDamage) {
            if (this.damage != other.damage) {
                isOtherItem = true
            }
        }

        if (!ignoreLore) {
            if (this.lore.content != other.lore.content) {
                isOtherItem = true
            }
        }

        if (!ignoreModifications) {
            if (this.modifications != other.modifications) {
                isOtherItem = true
            }
        }

        return !isOtherItem
    }

    fun isOther(
        other: Item,
        ignoreMaterial: Boolean = false,
        ignoreLabel: Boolean = false,
        ignoreSize: Boolean = false,
        ignoreDamage: Boolean = false,
        ignoreLore: Boolean = false,
        ignoreModifications: Boolean = false
    ) = !isSame(other, ignoreMaterial, ignoreLabel, ignoreSize, ignoreDamage, ignoreLore, ignoreModifications)

    @NotNull
    fun buildDisplayObject(bracketsColor: ChatColor = ChatColor.GRAY, nameColor: ChatColor? = null): TextComponent {
        val out = TextComponent("$bracketsColor[${nameColor?.toString() ?: ""}$label$bracketsColor]")
        val legacy = buildLegacy()

        out.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_ITEM, net.md_5.bungee.api.chat.hover.content.Item(legacy.type.name, legacy.amount, null))

        return out
    }

    @NotNull
    public override fun clone(): Item {
        return Item(material, label, size, damage, lore, modifications)
    }

    override fun serialize() = mapOf(
        "material" to material.name,
        "label" to label,
        "size" to size,
        "damage" to damage,
        "lore" to lore,
        "modifications" to modifications,
    )

    companion object {

        fun create(material: Material = Material.AIR, label: String = material.name, size: Int = 1, damage: Int = 0, lore: ItemLore = ItemLore(), modifications: List<EnchantmentData> = emptyList()): Item {
            return Item(material, label, size, damage, lore, modifications)
        }

        fun legacyEnchantmentsToData(legacy: Map<Enchantment, Int>): List<EnchantmentData> {
            val out = ArrayList<EnchantmentData>()

            legacy.forEach { (key, value) ->
                out.add(EnchantmentData(key, value))
            }

            return out
        }

        fun enchantmentsDataToLegacy(data: List<EnchantmentData>): Map<Enchantment, Int> {
            val out = HashMap<Enchantment, Int>()

            data.forEach {
                out[it.enchantmentType] = it.enchantmentLevel
            }

            return out
        }

    }

}