package de.fruxz.sdk

import de.fruxz.sdk.domain.PluginDesign
import de.fruxz.sdk.domain.display.Transmission
import de.fruxz.sdk.kernel.FruxzPlugin
import org.bukkit.Bukkit

class Main : FruxzPlugin() {

    override val pluginDesign: PluginDesign
        get() = PluginDesign("§6FruxzSDK §8// ")

    override val pluginName = "FruxzSDK"

    override fun preBootProcess() {
        TODO("Not yet implemented")
    }

    override fun bootProcess() {

        Transmission(this, "test").sendMessage(Bukkit.getOnlinePlayers())

    }

    override fun shutdownProcess() {
        TODO("Not yet implemented")
    }

}