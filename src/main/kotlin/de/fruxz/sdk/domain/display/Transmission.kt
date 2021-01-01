package de.fruxz.sdk.domain.display

import de.fruxz.sdk.configuration.ActivePreference
import de.fruxz.sdk.configuration.ActivePreferenceString
import de.fruxz.sdk.domain.event.transmission.PlayerReceiveTransmissionActionBarEvent
import de.fruxz.sdk.domain.event.transmission.SenderReceiveTransmissionMessageEvent
import de.fruxz.sdk.domain.event.transmission.TransmissionBroadcastActionBarEvent
import de.fruxz.sdk.domain.event.transmission.TransmissionBroadcastMessageEvent
import de.fruxz.sdk.domain.tasks.Tasky
import de.fruxz.sdk.kernel.FruxzPlugin
import net.md_5.bungee.api.chat.ComponentBuilder
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Server
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Transmission : Tasky {

    val transmissionContent = ComponentBuilder("${ChatColor.GRAY}")
    val plugin: FruxzPlugin

    constructor(plugin: FruxzPlugin, content: ComponentBuilder) {
        transmissionContent.append(plugin.pluginDesign.messagePrefix)
        content.create().forEach { transmissionContent.append(it) }

        this.plugin = plugin
    }

    constructor(plugin: FruxzPlugin, message: String) {
        transmissionContent.append(plugin.pluginDesign.messagePrefix)
        transmissionContent.append(message)

        this.plugin = plugin
    }

    constructor(plugin: FruxzPlugin, color: net.md_5.bungee.api.ChatColor) {
        transmissionContent.append(plugin.pluginDesign.messagePrefix)
        transmissionContent.color(color)

        this.plugin = plugin
    }

    constructor(plugin: FruxzPlugin, color: ChatColor) {
        transmissionContent.append(plugin.pluginDesign.messagePrefix)
        transmissionContent.append("$color")

        this.plugin = plugin
    }

    constructor(plugin: FruxzPlugin, content: ActivePreferenceString) {
        transmissionContent.append(plugin.pluginDesign.messagePrefix)
        transmissionContent.append(content.getMessage())

        this.plugin = plugin
    }

    constructor(plugin: FruxzPlugin, content: ActivePreference<*>?) {
        transmissionContent.append(plugin.pluginDesign.messagePrefix)
        transmissionContent.append("${content?.getContent()}")

        this.plugin = plugin
    }

    constructor(plugin: FruxzPlugin, content: TransmissionContentObjectable) {
        transmissionContent.append(plugin.pluginDesign.messagePrefix)
        transmissionContent.append(content.getObjectable())

        this.plugin = plugin
    }

    fun sendMessage(receiver: CommandSender, async: Boolean = true) {
        val event = SenderReceiveTransmissionMessageEvent(
            plugin = plugin,
            receiver = receiver,
            transmission = this,
            isCancelled = false,
        )

        plugin.callEvent(event)

        if (!event.isCancelled)
            task(plugin, isAsync = async) {
                event.receiver.sendMessage(*event.transmission.transmissionContent.create())
            }
    }

    fun sendMessage(vararg receiver: CommandSender, async: Boolean = true) {
        receiver.forEach { sendMessage(it, async) }
    }

    fun sendMessage(receivers: Collection<CommandSender>, async: Boolean = true) = sendMessage(receiver = receivers.toTypedArray(), async)

    fun sendActionBar(receiver: Player, async: Boolean = true) {
        val event = PlayerReceiveTransmissionActionBarEvent(
            plugin = plugin,
            receiver = receiver,
            transmission = this,
            isCancelled = false,
        )

        plugin.callEvent(event)

        if (!event.isCancelled)
            task(plugin, isAsync = async) {
                event.receiver.sendActionBar(*event.transmission.transmissionContent.create())
            }
    }

    fun sendActionBar(vararg player: Player, async: Boolean = true) {
        player.forEach { sendActionBar(it, async) }
    }

    fun sendActionBar(players: Collection<Player>, async: Boolean = true) = sendActionBar(player = players.toTypedArray(), async)

    fun broadcastMessage(async: Boolean = true) {
        val event = TransmissionBroadcastMessageEvent(plugin, this, false)

        plugin.callEvent(event)

        if (!event.isCancelled)
            task(plugin, async) {
                plugin.server.broadcast(*event.transmission.transmissionContent.create())
            }
    }

    fun broadcastActionBar(async: Boolean = true) {
        val event = TransmissionBroadcastActionBarEvent(plugin, this, false)

        plugin.callEvent(event)

        if (!event.isCancelled)
            task(plugin, async) {
                event.transmission.sendActionBar(plugin.server.onlinePlayers)
            }
    }

    fun broadcastMessage(server: Server? = Bukkit.getServer(), permission: String, async: Boolean = true) {
        val event = TransmissionBroadcastMessageEvent(plugin, this, false)

        plugin.callEvent(event)

        if (!event.isCancelled) {
            task(plugin, async) {
                (server ?: plugin.server).consoleSender.sendMessage(*event.transmission.transmissionContent.create())
                event.transmission.sendMessage(plugin.server.onlinePlayers.filter { it.hasPermission(permission) })
            }
        }
    }

    fun broadcastActionBar(server: Server? = Bukkit.getServer(), permission: String, async: Boolean = true) {
        val event = TransmissionBroadcastActionBarEvent(plugin, this, false)

        plugin.callEvent(event)

        if (!event.isCancelled)
            task(plugin, async) {
                event.transmission.sendActionBar((server ?: plugin.server).onlinePlayers.filter { it.hasPermission(permission) })
            }
    }

}