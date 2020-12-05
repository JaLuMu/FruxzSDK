package de.fruxz.sdk.kernel

import com.destroystokyo.paper.utils.PaperPluginLogger
import de.fruxz.sdk.configuration.ActivePreference
import de.fruxz.sdk.configuration.ActivePreferenceString
import de.fruxz.sdk.domain.display.Transmission
import org.bukkit.command.*
import org.bukkit.command.Command
import org.bukkit.entity.Player
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import java.util.*
import java.util.logging.Level
import kotlin.Exception
import kotlin.NoSuchElementException
import kotlin.collections.ArrayList
import kotlin.random.Random
import kotlin.random.nextInt

abstract class Command(val plugin: FruxzPlugin, val commandName: String) : CommandExecutor {

    companion object {
        val commandErrors = ArrayList<Triple<Calendar, String, String>>()
    }

    private val commandEngineLogger = PaperPluginLogger.getLogger("FruxzSDK - CommandEngine")

    /**
     * ***DO NOT OVERRIDE***
     */
    override fun onCommand(sender: CommandSender, command: Command, label: String, parameters: Array<out String>): Boolean {
        val executionProcess = this::onExecute

        if (commandPermissionLevel != CommandPermissionLevel.FRAMEWORK || (requiredCommandPermission != null && sender.hasPermission("$requiredCommandPermission"))) {
            if (requiredClientType == CommandClientAccessType.BOTH || (sender is Player && requiredClientType == CommandClientAccessType.PLAYER) || (sender is ConsoleCommandSender && requiredClientType == CommandClientAccessType.CONSOLE)) {

                val clientType = if (sender is Player) {
                    CommandClientType.PLAYER
                } else CommandClientType.CONSOLE

                try {
                    when (executionProcess(clientType, sender, this, label, parameters)) {

                        CommandResult.NOT_PERMITTED -> sendPermissionMessage(sender = sender)
                        CommandResult.WRONG_CLIENT -> sendClientTypeMessage(sender = sender)
                        CommandResult.WRONG_USAGE -> sendUsageMessage(sender = sender)
                        CommandResult.SUCCESS -> commandEngineLogger.log(
                            Level.FINEST,
                            "Executor ${sender.name} as ${clientType.name} successfully executed $commandName-command!"
                        )

                    }
                } catch (e: Exception) {
                    handleCommandException(exception = e, sender = sender, clientType = clientType)
                } catch (e: Exception) {
                    handleCommandException(exception = e, sender = sender, clientType = clientType)
                } catch (e: NullPointerException) {
                    handleCommandException(exception = e, sender = sender, clientType = clientType)
                } catch (e: NoSuchElementException) {
                    handleCommandException(exception = e, sender = sender, clientType = clientType)
                }

            } else
                sendClientTypeMessage(sender = sender, requiredClient = requiredClientType)
        } else
            sendPermissionMessage(sender = sender, requiredPermission = if (commandPermissionLevel == CommandPermissionLevel.FRAMEWORK) { requiredCommandPermission } else "${command.permission}")

        return true
    }

    /**
     * The [Command] function, which is called when the player enters the command
     * @param clientType the sender is PLAYER, or CONSOLE
     * @param sender the executor of the command
     * @param command the SDK-Command-Object
     * @param label the entered command-name (equals not name if aliases used)
     * @param args the entered extra-parameters of the executed command
     * @return the result of the command execution
     */
    abstract fun onExecute(clientType: CommandClientType, sender: CommandSender, command: de.fruxz.sdk.kernel.Command, label: String, args: Array<out String>): CommandResult

    abstract fun onTabComplete(clientType: CommandClientType, sender: CommandSender, command: de.fruxz.sdk.kernel.Command, label: String, args: Array<out String>): Collection<String>

    /**
     * This defines which type of client is required to run the command
     */
    @get:NotNull
    abstract val requiredClientType: CommandClientAccessType

    /**
     * This defines the contents of the command usage
     */
    @get:NotNull
    abstract val requiredCommandUsage: List<String>

    /**
     * This defines the required command execution permission
     */
    @get:Nullable
    abstract val requiredCommandPermission: String?

    /**
     * Which kind of command-permission-system is used?
     * @see CommandPermissionLevel
     */
    @get:NotNull
    abstract val commandPermissionLevel: CommandPermissionLevel

    fun buildCommandUsage(): String {
        return "/$commandName${requiredCommandUsage.joinToString(separator = " ", prefix = " ")}"
    }

    fun buildTabCompleter(): TabCompleter {
        return TabCompleter { sender, _, alias, args ->
            val clientType = if (sender is Player) { CommandClientType.PLAYER } else CommandClientType.CONSOLE
            val completion  = onTabComplete(clientType = clientType, sender = sender, command = this, label = alias, args = args)
            val out = ArrayList<String>()

            if (!completion.isNullOrEmpty()) {

                completion.forEach {
                    if (it.startsWith(args.last(), true)) {
                        out.add(it)
                    }
                }

            }

            if (out.isNullOrEmpty())
                out.add(" ")

            return@TabCompleter out
        }
    }

    fun sendPermissionMessage(sender: CommandSender, requiredPermission: String? = ::requiredCommandPermission.get()) {
        Transmission(plugin = plugin, message = plugin.pluginDesign.permissionMessage?.replace("<PERMISSION>", "$requiredPermission")
            ?: "§cTo execute this command you also need the permission '$requiredPermission'!").sendMessage(sender)
    }

    fun sendPermissionMessage(sender: CommandSender) = sendPermissionMessage(sender = sender, requiredPermission = ::requiredCommandPermission.get())

    fun sendUsageMessage(sender: CommandSender, commandUsage: String = buildCommandUsage()) {
        Transmission(plugin = plugin, message = plugin.pluginDesign.usageMessage?.replace("<USAGE>", buildCommandUsage())
            ?: "§cPlease keep this input syntax for this command: '$commandUsage'!").sendMessage(sender)
    }

    fun sendUsageMessage(sender: CommandSender) = sendUsageMessage(sender = sender, commandUsage = buildCommandUsage())

    fun sendClientTypeMessage(sender: CommandSender, requiredClient: CommandClientAccessType = ::requiredClientType.get()) {
        Transmission(plugin = plugin, message = plugin.pluginDesign.clientTypeMessage?.replace("<CLIENT>", requiredClientType.name)
            ?: "§cTo execute this command you must be a ${requiredClient.name}!").sendMessage(sender)
    }

    fun sendClientTypeMessage(sender: CommandSender) = sendClientTypeMessage(sender = sender, requiredClient = ::requiredClientType.get())

    fun sendFailMessage(sender: CommandSender) {
        Transmission(plugin = plugin, message = plugin.pluginDesign.useErrorMessage
            ?: "§c§lOOPS§c,an error has occurred! Please report this to a technical engineer, we are very sorry!").sendMessage(sender)
    }

    fun shootAnswer(target: CommandSender, stringAnswer: String, vararg replacors: Pair<String, String>) = Transmission(plugin, stringAnswer.let {
        val out = it

        for (replacor in replacors)
            out.replace(replacor.first, replacor.second)

        out
    }).sendMessage(target)

    fun shootAnswer(target: CommandSender, transmissionAnswer: Transmission) = Transmission(plugin, transmissionAnswer.transmissionContent).sendMessage(target)

    fun shootAnswer(target: CommandSender, preferenceStringAnswer: ActivePreferenceString, vararg replacors: Pair<String, String>) = Transmission(plugin, preferenceStringAnswer.getMessage(*replacors.toList().toTypedArray()))

    fun shootAnswer(target: CommandSender, preferenceAnswer: ActivePreference<String>, vararg replacors: Pair<String, String>) = Transmission(plugin, preferenceAnswer.getContent().let {
        val out = it

        for (replacor in replacors)
            out.replace(replacor.first, replacor.second)

        out
    })

    private fun addErrorToCache(id: String, stackTrace: String) {
        commandErrors.add(Triple(Calendar.getInstance(), id, stackTrace))

        if (commandErrors.size > 50) {
            commandErrors.removeAt(0)
        }

    }

    private fun handleCommandException(exception: Exception, sender: CommandSender, clientType: CommandClientType) {
        val errorID = '#' + Random.nextInt(0..99999).toString().padStart(5, '0')

        commandEngineLogger.log(Level.WARNING,"Executor ${sender.name} as ${clientType.name} caused an error at execution of $commandName-command! ErrorID: $errorID")
        commandEngineLogger.log(Level.WARNING, "--- [ START $errorID ] ------------------------")
        exception.printStackTrace()
        commandEngineLogger.log(Level.WARNING, "--- [  END $errorID  ] ------------------------")

        addErrorToCache(errorID, exception.stackTraceToString())

        sendFailMessage(sender) // send user friendly error message
    }

    enum class CommandResult {
        SUCCESS, NOT_PERMITTED, WRONG_USAGE, WRONG_CLIENT
    }

    enum class CommandClientType {
        PLAYER, CONSOLE
    }

    enum class CommandClientAccessType {
        BOTH, PLAYER, CONSOLE
    }

    /**
     * Which kind of command-permission-system
     * [FRAMEWORK] FruxzSDK's command-permission-system
     * [LEGACY] Bukkit's command-permission-system
     * [NOTHING] No ones command-permission-system
     */
    enum class CommandPermissionLevel {
        FRAMEWORK, LEGACY, NOTHING
    }

}