package de.fruxz.sdk.domain

import org.bukkit.configuration.serialization.ConfigurationSerializable

/**
 * This class helps to easily save/write/create an unique plugin-design
 */
class PluginDesign : ConfigurationSerializable {
    val messagePrefix: String
    val permissionMessage: String?
    val usageMessage: String?
    val clientTypeMessage: String?
    val useErrorMessage: String?

    constructor(
        messagePrefix: String,
        permissionMessage: String? = null,
        usageMessage: String? = null,
        clientTypeMessage: String? = null,
        useErrorMessage: String? = null,
    ) {
        this.messagePrefix = messagePrefix
        this.permissionMessage = permissionMessage
        this.usageMessage = usageMessage
        this.clientTypeMessage = clientTypeMessage
        this.useErrorMessage = useErrorMessage
    }

    constructor(map: Map<String, Any>) {
        messagePrefix = "${map["messagePrefix"]}"
        permissionMessage = "${map["permissionMessage"]}"
        usageMessage = "${map["usageMessage"]}"
        clientTypeMessage = "${map["clientTypeMessage"]}"
        useErrorMessage = "${map["useErrorMessage"]}"
    }

    override fun serialize() = mapOf(
        "messagePrefix" to messagePrefix,
        "permissionMessage" to permissionMessage,
        "usageMessage" to usageMessage,
        "clientTypeMessage" to clientTypeMessage,
        "useErrorMessage" to useErrorMessage,
    )
}
