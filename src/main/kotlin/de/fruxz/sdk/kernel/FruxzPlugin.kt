package de.fruxz.sdk.kernel

import de.fruxz.sdk.domain.PluginDesign
import de.fruxz.sdk.domain.service.SystemService
import de.fruxz.sdk.util.BoolUtils
import org.bukkit.command.CommandExecutor
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.annotations.NotNull
import java.util.logging.Level
import kotlin.reflect.KClass

/**
 * This class is like a [JavaPlugin] from the basic system,
 * only this class extends the [JavaPlugin] class with additional
 * functions for the FruxzSDK functions and systems, and does
 * legacy systems easier and smarter
 */
abstract class FruxzPlugin : JavaPlugin() {

    @get:NotNull
    abstract val pluginDesign: PluginDesign

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
    @Deprecated(message = "With FruxzSDK Bukkit-Commands are deprecated!", level = DeprecationLevel.WARNING, replaceWith = ReplaceWith("addCommand(command)"))
    fun addCommand(name: String, executor: CommandExecutor) {
        getCommand(name)?.setExecutor(executor)
    }

    fun addCommand(@NotNull command: Command) {
        val bukkitCommand = getCommand(command.commandName)

        if (bukkitCommand != null) {

            bukkitCommand.setExecutor(command)
            bukkitCommand.tabCompleter = command.buildTabCompleter()
            bukkitCommand.usage = command.buildCommandUsage()

            if (command.commandPermissionLevel == Command.CommandPermissionLevel.LEGACY)
                bukkitCommand.permission = command.requiredCommandPermission

        } else
            throw IllegalArgumentException("Cannot find Command with name '${command.commandName}' in plugin.yml!")

    }

    /**
     * This function adds an event listener for Bukkit
     * Paper, Spigot and FruxzSDK events are added.
     */
    fun addHandler(listener: Listener) {
        localPluginManager.registerEvents(listener, this)
    }

    /**
     * Registering an ConfigurationSerializable
     */
    fun registerSerializable(clazz: Class<out ConfigurationSerializable>) {
        ConfigurationSerialization.registerClass(clazz)
    }

    /**
     * Registering an ConfigurationSerializable
     */
    fun registerSerializable(clazz: KClass<out ConfigurationSerializable>) {
        ConfigurationSerialization.registerClass(clazz.java)
    }

    /**
     * Starting an service with its parameters
     */
    fun bootService(service: SystemService) {
        server.logger.log(Level.INFO, "Plugin '${this.name}'//($pluginName) is booting service '${service::class.simpleName}'//(${service::class.qualifiedName}) [${BoolUtils().boolSelector(service.provider.isAsync, "Async", "Sync")}]")
        service.boot()
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