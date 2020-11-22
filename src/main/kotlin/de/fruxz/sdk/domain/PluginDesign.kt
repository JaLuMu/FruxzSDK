package de.fruxz.sdk.domain

import org.bukkit.configuration.serialization.ConfigurationSerializable

/**
 * @param messagePrefix The prefix of the transmissions
 * @param permissionMessage Variable: '<PERMISSION>' equals required permission
 * @param usageMessage Variable: '<USAGE>' equals command usage
 * @param clientTypeMessage Variable: '<CLIENT>' equals required type of client
 */
class PluginDesign : ConfigurationSerializable {
    val messagePrefix: String
    val permissionMessage: String?
    val usageMessage: String?
    val clientTypeMessage: String?

    constructor(
        messagePrefix: String,
        permissionMessage: String? = null,
        usageMessage: String? = null,
        clientTypeMessage: String? = null
    ) {
        this.messagePrefix = messagePrefix
        this.permissionMessage = permissionMessage
        this.usageMessage = usageMessage
        this.clientTypeMessage = clientTypeMessage
    }

    constructor(map: Map<String, Any>) {
        messagePrefix = "${map["messagePrefix"]}"
        permissionMessage = "${map["permissionMessage"]}"
        usageMessage = "${map["usageMessage"]}"
        clientTypeMessage = "${map["clientTypeMessage"]}"
    }

    override fun serialize() = mapOf(
        "messagePrefix" to messagePrefix,
        "permissionMessage" to permissionMessage,
        "usageMessage" to usageMessage,
        "clientTypeMessage" to clientTypeMessage,
    )
}
