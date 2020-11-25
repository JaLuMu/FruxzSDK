package de.fruxz.sdk.domain.display

import de.fruxz.sdk.configuration.ActivePreference
import de.fruxz.sdk.configuration.ActivePreferenceString
import de.fruxz.sdk.kernel.FruxzPlugin
import net.md_5.bungee.api.chat.ComponentBuilder
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Server
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Transmission {

    val transmissionContent = ComponentBuilder("${ChatColor.GRAY}")
    val plugin: FruxzPlugin?

    constructor(plugin: FruxzPlugin?, content: ComponentBuilder) {
        transmissionContent.append(plugin?.pluginDesign?.messagePrefix ?: "§7⋙ ")
        content.create().forEach { transmissionContent.append(it) }

        this.plugin = plugin
    }

    constructor(plugin: FruxzPlugin?, message: String) {
        transmissionContent.append(plugin?.pluginDesign?.messagePrefix ?: "§7⋙ ")
        transmissionContent.append(message)

        this.plugin = plugin
    }

    constructor(plugin: FruxzPlugin?, color: net.md_5.bungee.api.ChatColor) {
        transmissionContent.append(plugin?.pluginDesign?.messagePrefix ?: "§7⋙ ")
        transmissionContent.color(color)

        this.plugin = plugin
    }

    constructor(plugin: FruxzPlugin?, color: ChatColor) {
        transmissionContent.append(plugin?.pluginDesign?.messagePrefix ?: "§7⋙ ")
        transmissionContent.append("$color")

        this.plugin = plugin
    }

    constructor(plugin: FruxzPlugin?, content: ActivePreferenceString) {
        transmissionContent.append(plugin?.pluginDesign?.messagePrefix ?: "§7⋙ ")
        transmissionContent.append(content.getMessage())

        this.plugin = plugin
    }

    constructor(plugin: FruxzPlugin?, content: ActivePreference<*>?) {
        transmissionContent.append(plugin?.pluginDesign?.messagePrefix ?: "§7⋙ ")
        transmissionContent.append("${content?.getContent()}")

        this.plugin = plugin
    }

    constructor(plugin: FruxzPlugin?, content: TransmissionContentObjectable) {
        transmissionContent.append(plugin?.pluginDesign?.messagePrefix ?: "§7⋙ ")
        transmissionContent.append(content.getObjectable())

        this.plugin = plugin
    }

    fun sendMessage(receiver: CommandSender) {
        receiver.sendMessage(*transmissionContent.create())
    }

    fun sendMessage(vararg receiver: CommandSender) {
        receiver.forEach { sendMessage(it) }
    }

    fun sendMessage(receivers: Collection<CommandSender>) = sendMessage(receiver = receivers.toTypedArray())

    fun sendActionBar(receiver: Player) {
        receiver.sendActionBar(*transmissionContent.create())
    }

    fun sendActionBar(vararg player: Player) {
        player.forEach { sendActionBar(it) }
    }

    fun sendActionBar(players: Collection<Player>) = sendActionBar(player = players.toTypedArray())

    fun broadcastMessage(server: Server? = Bukkit.getServer()) {
        if (plugin != null || server != null) {
            (plugin?.server ?: server)!!.broadcast(*transmissionContent.create())
        } else
            throw NullPointerException("The plugin from constructor or specified server must be non-null!")
    }

    fun broadcastActionBar(server: Server? = Bukkit.getServer()) {
        if (plugin != null || server != null) {
            sendActionBar((plugin?.server ?: server)!!.onlinePlayers)
        } else
            throw NullPointerException("The plugin from constructor or specified server must be non-null!")
    }

    fun broadcastMessage(server: Server? = Bukkit.getServer(), permission: String) {
        if (plugin != null || server != null) {
            (plugin?.server ?: server)!!.broadcast(*transmissionContent.create())
            sendMessage((plugin?.server ?: server)!!.onlinePlayers.filter { it.hasPermission(permission) })
        } else
            throw NullPointerException("The plugin from constructor or specified server must be non-null!")
    }

    fun broadcastActionBar(server: Server? = Bukkit.getServer(), permission: String) {
        if (plugin != null || server != null) {
            sendActionBar((plugin?.server ?: server)!!.onlinePlayers.filter { it.hasPermission(permission) })
        } else
            throw NullPointerException("The plugin from constructor or specified server must be non-null!")
    }

}