package de.fruxz.sdk.domain.container

import com.destroystokyo.paper.MaterialTags
import de.fruxz.sdk.domain.User
import de.fruxz.sdk.domain.display.TransmissionContentObjectable
import de.fruxz.sdk.util.ListUtils
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.SkullMeta
import org.jetbrains.annotations.NotNull
import java.util.*

class Item : Cloneable, ConfigurationSerializable, TransmissionContentObjectable {

    var material: Material
    var size: Int
    var damage: Int
    var label: String
    var lore: ItemLore
    var modifications: ArrayList<EnchantmentData>
    val flags: ArrayList<ItemFlag>
    var skullOwner: UUID?

    constructor() {
        material = Material.AIR
        label = ChatColor.WHITE.toString() + material.name
        size = 1
        damage = 0
        lore = ItemLore()
        modifications = ArrayList()
        flags = ArrayList()
        skullOwner = null
    }

    constructor(material: Material) {
        this.material = material
        label = ChatColor.WHITE.toString() + material.name
        size = 1
        damage = 0
        lore = ItemLore()
        modifications = ArrayList()
        flags = ArrayList()
        skullOwner = null
    }

    constructor(itemStack: ItemStack) {
        label = if (itemStack.itemMeta.hasDisplayName()) itemStack.itemMeta.displayName else ChatColor.WHITE.toString() + itemStack.type.name
        material = itemStack.type
        size = itemStack.amount
        damage = if (itemStack.itemMeta is Damageable) { (itemStack.itemMeta as Damageable).damage } else 0
        lore = ItemLore(itemStack.lore)
        modifications = ArrayList(legacyEnchantmentsToData(itemStack.enchantments))
        flags = ArrayList(itemStack.itemFlags.toList())
        skullOwner = if (itemStack.type == Material.PLAYER_HEAD) {
            (itemStack.itemMeta as SkullMeta).owningPlayer?.uniqueId
        } else
            null
    }

    constructor(material: Material, label: String = material.name, size: Int = 1, damage: Int = 0, lore: ItemLore = ItemLore(), modifications: List<EnchantmentData> = emptyList(), flags: List<ItemFlag> = emptyList(), skullOwner: UUID? = null) {
        this.material = material
        this.size = size
        this.label = label
        this.damage = damage
        this.lore = lore
        this.modifications = ArrayList(modifications)
        this.flags = ArrayList(flags)
        this.skullOwner = skullOwner
    }

    constructor(map: Map<String, Any>) {
        material = Material.matchMaterial("" + map["material"])!!
        label = "" + map["label"]
        size = (map["size"] as Number).toInt()
        damage = (map["damage"] as Number).toInt()
        lore = map["lore"] as ItemLore
        modifications = ArrayList(map["modifications"] as List<EnchantmentData>)
        flags = ListUtils().convert(map["flags"] as List<String>) { ItemFlag.valueOf(it) }
        skullOwner = UUID.fromString("" + map["skullOwner"])
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

        itemMeta.addItemFlags(*flags.toTypedArray())

        if (skullOwner != null && MaterialTags.SKULLS.isTagged(itemStack)) {
            val skullMeta = itemStack.itemMeta as SkullMeta

            skullOwner?.let { skullMeta.owningPlayer = Bukkit.getOfflinePlayer(it) }

            itemStack.itemMeta = skullMeta
        } else
            itemStack.itemMeta = itemMeta

        return itemStack
    }

    fun buildMeta(): ItemMeta {
        val itemMeta = Bukkit.getItemFactory().getItemMeta(material)

        itemMeta.setDisplayName(label)
        itemMeta.lore = lore.content

        if (!flags.isNullOrEmpty())
            itemMeta.addItemFlags(*flags.toTypedArray())

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
        ignoreModifications: Boolean = false,
        ignoreFlags: Boolean = false,

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

        if (!ignoreFlags) {
            if (this.flags != other.flags) {
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
        ignoreModifications: Boolean = false,
        ignoreFlags: Boolean = false,
    ) = !isSame(other, ignoreMaterial, ignoreLabel, ignoreSize, ignoreDamage, ignoreLore, ignoreModifications, ignoreFlags)

    @NotNull
    fun buildDisplayObject(bracketsColor: ChatColor = ChatColor.GRAY, nameColor: ChatColor? = null): TextComponent {
        val out = TextComponent("$bracketsColor[${nameColor?.toString() ?: ""}$label$bracketsColor]")
        val legacy = buildLegacy()

        out.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_ITEM, net.md_5.bungee.api.chat.hover.content.Item(legacy.type.name, legacy.amount, null))

        return out
    }

    fun addItemFlags(vararg flags: ItemFlag) {
        this.flags.addAll(flags)
    }

    fun removeItemFlags(vararg flags: ItemFlag) {
        this.flags.removeAll(flags)
    }

    fun containFlag(flag: ItemFlag) = flags.contains(flag)

    override fun getObjectable() = buildDisplayObject()

    @NotNull
    public override fun clone(): Item {
        return Item(material, label, size, damage, lore, modifications, flags)
    }

    override fun serialize() = mapOf(
        "material" to material.name,
        "label" to label,
        "size" to size,
        "damage" to damage,
        "lore" to lore,
        "modifications" to modifications,
        "flags" to ListUtils().convert(flags) { it.name },
        "skullOwner" to skullOwner.toString(),
    )

    companion object {

        fun create(material: Material = Material.AIR, label: String = material.name, size: Int = 1, damage: Int = 0, lore: ItemLore = ItemLore(), modifications: List<EnchantmentData> = emptyList(), flags: List<ItemFlag> = emptyList(), skullOwner: UUID? = null): Item {
            return Item(material, label, size, damage, lore, modifications, flags)
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

        fun createHead(offlinePlayer: OfflinePlayer) = Item(Material.PLAYER_HEAD).let {
            it.skullOwner = offlinePlayer.uniqueId
            it
        }

        fun createHead(uniqueIdentity: UUID) = createHead(offlinePlayer = Bukkit.getOfflinePlayer(uniqueIdentity))

        fun createHead(user: User) = createHead(uniqueIdentity = user.player.uniqueId)

    }

}