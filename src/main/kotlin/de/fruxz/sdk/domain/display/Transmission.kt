package de.fruxz.sdk.domain.display

import de.fruxz.sdk.kernel.FruxzPlugin
import net.md_5.bungee.api.chat.ComponentBuilder
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Transmission {

    val transmissionContent = ComponentBuilder("${ChatColor.GRAY}")

    constructor(plugin: FruxzPlugin?, content: ComponentBuilder) {
        transmissionContent.append(plugin?.pluginDesign?.messagePrefix ?: "§7⋙ ")
        content.create().forEach { transmissionContent.append(it) }
    }

    constructor(plugin: FruxzPlugin?, message: String) {
        transmissionContent.append(plugin?.pluginDesign?.messagePrefix ?: "§7⋙ ")
        transmissionContent.append(message)
    }

    constructor(plugin: FruxzPlugin?, color: net.md_5.bungee.api.ChatColor) {
        transmissionContent.append(plugin?.pluginDesign?.messagePrefix ?: "§7⋙ ")
        transmissionContent.color(color)
    }

    constructor(plugin: FruxzPlugin?, color: ChatColor) {
        transmissionContent.append(plugin?.pluginDesign?.messagePrefix ?: "§7⋙ ")
        transmissionContent.append("$color")
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

}