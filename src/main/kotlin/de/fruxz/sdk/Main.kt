package de.fruxz.sdk

import de.fruxz.sdk.domain.FlexibleLocationBundle
import de.fruxz.sdk.domain.PluginDesign
import de.fruxz.sdk.domain.container.*
import de.fruxz.sdk.handler.WeatherHandler
import de.fruxz.sdk.kernel.FruxzPlugin

/**
 * This class helps to build the framework-system on the minecraft-server
 */
class Main : FruxzPlugin() {

    companion object {
        lateinit var instance: Main
    }

    override var pluginDesign = PluginDesign("§6FruxzSDK §8// ")

    override val pluginName = "FruxzSDK"

    override fun preBootProcess() {

        registerSerializable(ItemLore::class)
        registerSerializable(Item::class)
        registerSerializable(InventoryUI::class)
        registerSerializable(EnchantmentData::class)
        registerSerializable(PluginDesign::class)
        registerSerializable(ItemBundle::class)
        registerSerializable(FlexibleLocationBundle::class)

    }

    override fun bootProcess() {
        instance = this

        addHandler(WeatherHandler())

    }

    override fun shutdownProcess() { }

}