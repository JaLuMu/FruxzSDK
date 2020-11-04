package de.fruxz.sdk.kernel

import org.bukkit.command.CommandExecutor
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.annotations.NotNull

/**
 * This class is like a [JavaPlugin] from the basic system,
 * only this class extends the [JavaPlugin] class with additional
 * functions for the FruxzSDK functions and systems, and does
 * legacy systems easier and smarter
 */
abstract class FruxzPlugin : JavaPlugin() {

    /**
     * This string represents the plugin name
     * which the plugin should have as a finished Artifact.
     */
    @get:NotNull
    abstract val pluginName: String

    /**
     * This protected variable represents the current
     * PluginManager of the server where the plugin is running
     * becomes there. Can be used e.g. for listener simply
     * become.
     * @sample addHandler
     */
    @get:NotNull
    protected val localPluginManager = server.pluginManager

    /**
     * This method simply adds a legacy command
     * to the server via the [executor] with the name
     * add [name], if [name] is taken from the plugin.yml
     * can be fetched / exists.
     */
    fun addCommand(name: String, executor: CommandExecutor) {
        getCommand(name)?.setExecutor(executor)
    }

    /**
     * This function adds an event listener for Bukkit
     * Paper, Spigot and FruxzSDK events are added.
     */
    fun addHandler(listener: Listener) {
        localPluginManager.registerEvents(listener, this)
    }

    /**
     * Replacing the [onLoad] method
     */
    abstract fun preBootProcess()

    /**
     * Replacing the [onEnable] method
     */
    abstract fun bootProcess()

    /**
     * Replacing the [onDisable]
     */
    abstract fun shutdownProcess()

    /**
     * ***DO NOT OVERRIDE!***
     */
    override fun onLoad() {
        preBootProcess()
        super.onLoad()
    }

    /**
     * ***DO NOT OVERRIDE!***
     */
    override fun onEnable() {
        bootProcess()
        super.onEnable()
    }

    /**
     * ***DO NOT OVERRIDE!***
     */
    override fun onDisable() {
        shutdownProcess()
        super.onDisable()
    }

}