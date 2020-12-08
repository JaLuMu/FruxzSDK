package de.fruxz.sdk.domain.manager

import org.bukkit.Bukkit
import java.util.*

/**
 * This interface helps to easily create an class,
 * which modify or manage players
 */
interface UserManager {

    val user: UUID

    fun getOfflinePlayer() = Bukkit.getOfflinePlayer(user)

}