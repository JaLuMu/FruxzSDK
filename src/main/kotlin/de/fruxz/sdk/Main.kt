package de.fruxz.sdk

import de.fruxz.sdk.domain.FlexibleLocationBundle
import de.fruxz.sdk.domain.PluginDesign
import de.fruxz.sdk.domain.container.*
import de.fruxz.sdk.domain.sound.SoundBuilder
import de.fruxz.sdk.domain.sound.SoundData
import de.fruxz.sdk.domain.sound.SoundMelody
import de.fruxz.sdk.domain.timer.TimerProviderService
import de.fruxz.sdk.handler.CombatHandler
import de.fruxz.sdk.handler.InteractHandler
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
        registerSerializable(SoundData::class)
        registerSerializable(SoundMelody::class)


    }

    override fun bootProcess() {
        instance = this

        addHandler(WeatherHandler())
        addHandler(CombatHandler())
        addHandler(InteractHandler())

        bootService(TimerProviderService())

    }

    override fun shutdownProcess() { }

}