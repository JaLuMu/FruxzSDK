package de.fruxz.sdk.util

import net.md_5.bungee.api.ChatColor
import org.bukkit.OfflinePlayer

class PlayerListUtils {

    fun generateBooleanColoredPlayers(players: Collection<OfflinePlayer>, yes: ChatColor = ChatColor.GREEN, no: ChatColor = ChatColor.RED, checkment: (OfflinePlayer) -> Boolean): StringBuilder {
        val out = StringBuilder()

        players.forEach {
            val color = if (checkment(it)) yes else no

            if (out.isNotEmpty() && out.isNotBlank())
                out.append("§7, ")

            out.append("$color${it.name}")
        }

        return out
    }

    fun generateBooleanColoredPlayers(vararg players: OfflinePlayer, yes: ChatColor = ChatColor.GREEN, no: ChatColor = ChatColor.RED, checkment: (OfflinePlayer) -> Boolean) =
        generateBooleanColoredPlayers(players.toList(), yes, no, checkment)

}