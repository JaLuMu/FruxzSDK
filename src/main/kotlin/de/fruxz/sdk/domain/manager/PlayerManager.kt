package de.fruxz.sdk.domain.manager

import de.fruxz.sdk.domain.User
import org.bukkit.entity.Player

/**
 * This interface helps to easily create an class,
 * which modify or manage players
 */
interface PlayerManager : UserManager {

    val player: Player
    val interactor: User
        get() = User(player = player)

}