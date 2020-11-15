package de.fruxz.sdk.domain.container

import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
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
        label = material.name
        size = 1
        damage = 0
        lore = ItemLore()
        modifications = ArrayList()
    }

    constructor(material: Material) {
        this.material = material
        label = material.name
        size = 1
        damage = 0
        lore = ItemLore()
        modifications = ArrayList()
    }

    constructor(itemStack: ItemStack) {
        label = itemStack.itemMeta.displayName
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

    fun buildLegacy(): ItemStack {
        val itemStack = ItemStack(material, size, damage.toShort())
        val itemMeta = itemStack.itemMeta

        itemMeta.setDisplayName(label)
        itemMeta.lore = lore.content

        itemStack.addEnchantments(enchantmentsDataToLegacy(modifications))

        return itemStack
    }

    @NotNull
    override fun clone(): Item {
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