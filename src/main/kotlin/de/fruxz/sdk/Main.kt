package de.fruxz.sdk

import de.fruxz.sdk.domain.PluginDesign
import de.fruxz.sdk.domain.User
import de.fruxz.sdk.domain.container.*
import de.fruxz.sdk.domain.display.Transmission
import de.fruxz.sdk.kernel.FruxzPlugin
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerialization

class Main : FruxzPlugin() {

    override val pluginDesign: PluginDesign
        get() = PluginDesign("§6FruxzSDK §8// ")

    override val pluginName = "FruxzSDK"

    override fun preBootProcess() {

        registerSerializable(ItemLore::class)
        registerSerializable(Item::class)
        registerSerializable(InventoryUI::class)
        registerSerializable(EnchantmentData::class)

    }

    override fun bootProcess() { }

    override fun shutdownProcess() { }

}