package de.fruxz.sdk.domain.display

import de.fruxz.sdk.configuration.ActivePreference
import de.fruxz.sdk.configuration.ActivePreferenceString
import de.fruxz.sdk.domain.event.PlayerReceiveTransmissionActionBarEvent
import de.fruxz.sdk.domain.event.SenderReceiveTransmissionMessageEvent
import de.fruxz.sdk.kernel.FruxzPlugin
import net.md_5.bungee.api.chat.ComponentBuilder
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Server
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Transmission {

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

    fun sendMessage(receiver: CommandSender) {
        val event = SenderReceiveTransmissionMessageEvent(
            plugin = plugin,
            receiver = receiver,
            transmission = this,
            isCancelled = false,
        )

        plugin.callEvent(event)

        if (!event.isCancelled)
            event.receiver.sendMessage(*event.transmission.transmissionContent.create())
    }

    fun sendMessage(vararg receiver: CommandSender) {
        receiver.forEach { sendMessage(it) }
    }

    fun sendMessage(receivers: Collection<CommandSender>) = sendMessage(receiver = receivers.toTypedArray())

    fun sendActionBar(receiver: Player) {
        val event = PlayerReceiveTransmissionActionBarEvent(
            plugin = plugin,
            receiver = receiver,
            transmission = this,
            isCancelled = false,
        )

        plugin.callEvent(event)

        if (!event.isCancelled)
            event.receiver.sendActionBar(*event.transmission.transmissionContent.create())
    }

    fun sendActionBar(vararg player: Player) {
        player.forEach { sendActionBar(it) }
    }

    fun sendActionBar(players: Collection<Player>) = sendActionBar(player = players.toTypedArray())

    fun broadcastMessage() {
        plugin.server.broadcast(*transmissionContent.create())
    }

    fun broadcastActionBar() {
        sendActionBar(plugin.server.onlinePlayers)
    }

    fun broadcastMessage(server: Server? = Bukkit.getServer(), permission: String) {
            plugin.server.broadcast(*transmissionContent.create())
            sendMessage(plugin.server.onlinePlayers.filter { it.hasPermission(permission) })
    }

    fun broadcastActionBar(server: Server? = Bukkit.getServer(), permission: String) {
            sendActionBar(plugin.server.onlinePlayers.filter { it.hasPermission(permission) })
    }

}