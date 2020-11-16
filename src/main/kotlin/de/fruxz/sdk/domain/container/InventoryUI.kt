package de.fruxz.sdk.domain.container

import org.bukkit.Bukkit
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.inventory.Inventory

class InventoryUI : ConfigurationSerializable {

    val contents: HashMap<Int, Item>
    val label: String
    val size: InventorySize

    constructor(label: String = "container", size: InventorySize = InventorySize.SMALL) {
        this.contents = HashMap()
        this.label = label
        this.size = size
    }

    constructor(map: Map<String, Any>) {

        this.label = "${map["label"]}"
        this.size = InventorySize.valueOf("${map["size"]}")
        this.contents = HashMap(map["content"] as Map<Int, Item>)

    }

    fun buildInventory(): Inventory {
        val inventory = Bukkit.createInventory(null, )
    }

    override fun serialize() = mapOf(
        "label" to label,
        "size" to size,
        "content" to contents
    )

}