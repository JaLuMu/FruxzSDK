package de.fruxz.sdk

import de.fruxz.sdk.domain.PluginDesign
import de.fruxz.sdk.domain.container.*
import de.fruxz.sdk.kernel.FruxzPlugin

class Main : FruxzPlugin() {

    companion object {
        lateinit var instance: Main
    }

    override val pluginDesign: PluginDesign
        get() = PluginDesign("§6FruxzSDK §8// ")

    override val pluginName = "FruxzSDK"

    override fun preBootProcess() {

        registerSerializable(ItemLore::class)
        registerSerializable(Item::class)
        registerSerializable(InventoryUI::class)
        registerSerializable(EnchantmentData::class)

    }

    override fun bootProcess() {
        instance = this
    }

    override fun shutdownProcess() { }

}