package de.fruxz.sdk.configuration

import de.fruxz.sdk.domain.User
import org.bukkit.entity.Player
import java.util.*

class PlayerPreference<T> : ActivePreference<T> {

    constructor(player: Player, activeFile: ActiveFileController, path: String, default: T, useCache: Boolean = false) : super(activeFile, "$path.${player.uniqueId}", default, useCache)

    constructor(user: User, activeFile: ActiveFileController, path: String, default: T, useCache: Boolean = false) : super(activeFile, "$path.${user.player.uniqueId}", default, useCache)

    constructor(id: UUID, activeFile: ActiveFileController, path: String, default: T, useCache: Boolean = false) : super(activeFile, "$path.${id}", default, useCache)

}