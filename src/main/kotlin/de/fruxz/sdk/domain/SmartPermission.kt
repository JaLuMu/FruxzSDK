package de.fruxz.sdk.domain

import de.fruxz.sdk.kernel.FruxzPlugin

class SmartPermission(val plugin: FruxzPlugin, permission: String) {

    val basePermission: String = permission
    val fullPermission: String
        get() { return "${plugin.name}.$basePermission" }

}