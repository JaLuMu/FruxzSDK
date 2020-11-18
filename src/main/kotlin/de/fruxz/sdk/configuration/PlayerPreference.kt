package de.fruxz.sdk.configuration

import org.bukkit.entity.Player

class PlayerPreference<T>(player: Player, activeFile: ActiveFileController, path: String, default: T, useCache: Boolean = false) :
    ActivePreference<T>(activeFile, "$path//${player.uniqueId}", default, useCache)