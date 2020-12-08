package de.fruxz.sdk.util

import net.md_5.bungee.api.ChatColor
import org.bukkit.OfflinePlayer

class PlayerListUtils {

    @Deprecated(level = DeprecationLevel.ERROR, message = "")
    fun generateLegacyBooleanColoredPlayers(players: Collection<OfflinePlayer>, yes: ChatColor = ChatColor.GREEN, no: ChatColor = ChatColor.RED, checkment: (OfflinePlayer) -> Boolean): StringBuilder {
        val out = StringBuilder()

        players.forEach {
            val color = if (checkment(it)) yes else no

            if (out.isNotEmpty() && out.isNotBlank())
                out.append("§7, ")

            out.append("$color${it.name}")
        }

        return out
    }

    fun generateBooleanColored(players: Collection<OfflinePlayer>, yes: ChatColor = ChatColor.GREEN, no: ChatColor = ChatColor.RED, checkment: (OfflinePlayer) -> Boolean): String {
        return (ListUtils().convert(players) { player -> "${checkment(player).let { if (it) yes else no }}${player.name}"}).joinToString("§7, ")
    }

}