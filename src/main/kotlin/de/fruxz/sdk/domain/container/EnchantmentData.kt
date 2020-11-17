package de.fruxz.sdk.domain.container

import org.bukkit.NamespacedKey
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.enchantments.Enchantment

class EnchantmentData : ConfigurationSerializable {

    val enchantmentType: Enchantment
    val enchantmentLevel: Int

    constructor(enchantmentType: Enchantment, enchantmentLevel: Int) {
        this.enchantmentType = enchantmentType
        this.enchantmentLevel = enchantmentLevel
    }

    constructor(map: Map<String, Any>) {
        enchantmentType = Enchantment.getByKey(map["type"] as NamespacedKey?)!!
        enchantmentLevel = (map["level"] as Number).toInt()
    }

    override fun serialize() = mapOf(
        "type" to enchantmentType.key,
        "level" to enchantmentLevel
    )
}
