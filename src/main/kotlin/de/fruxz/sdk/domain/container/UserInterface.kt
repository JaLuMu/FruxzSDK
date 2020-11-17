package de.fruxz.sdk.domain.container

import de.fruxz.sdk.domain.User
import org.bukkit.entity.Player

interface UserInterface {

    fun sendUser(user: Player)

    fun sendUser(user: User)

}