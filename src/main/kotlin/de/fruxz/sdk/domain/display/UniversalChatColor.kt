package de.fruxz.sdk.domain.display

import net.md_5.bungee.api.ChatColor
import java.awt.Color

/**
 * This class helps to easily generate an accepted ChatColor
 * out of different kind of Color-Generators
 */
class UniversalChatColor {

    var basicChatColor: ChatColor

    constructor(color: Color) {
        basicChatColor = ChatColor.of(color)
    }

    constructor(r: Int, g: Int, b: Int) {
        basicChatColor = ChatColor.of(Color(r, g, b))
    }

    constructor(r: Float, g: Float, b: Float) {
        basicChatColor = ChatColor.of(Color(r, g, b))
    }

    constructor(color: org.bukkit.ChatColor) {
        basicChatColor = ChatColor.valueOf(color.name)
    }

    override fun toString(): String {
        return "$basicChatColor"
    }

}