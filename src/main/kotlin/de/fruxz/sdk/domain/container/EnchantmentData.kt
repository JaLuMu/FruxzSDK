package de.fruxz.sdk.domain.container

import org.bukkit.NamespacedKey
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.enchantments.Enchantment

/**
 * This class helps to store values and data of an enchantment for an [Item] or an ItemStack and thus
 * make it storable and readable as a bundle for the use of a file storage.
 *
 * WIKI: [Object: EnchantmentData](https://github.com/TheFruxz/FruxzSDK/wiki/Object:-EnchantmentData)
 */
class EnchantmentData : ConfigurationSerializable {

    /**
     * Specifies the type of enchantment which would be applicable to e.g. an [Item].
     */
    val enchantmentType: Enchantment

    /**
     * Specifies the strength or level of the enchantment that would be applied to an [Item] with [enchantmentType], for example.
     */
    val enchantmentLevel: Int

    /**
     * Generates an [EnchantmentData] using the various subparameters needed for the [EnchantmentData] structure.
     * @param enchantmentType specifies the type of enchantment
     * @param enchantmentLevel specifies the level/strength of the final enchantment
     */
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
