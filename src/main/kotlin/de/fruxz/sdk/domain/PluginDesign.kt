package de.fruxz.sdk.domain

/**
 * @param messagePrefix The prefix of the transmissions
 * @param permissionMessage Variable: '<PERMISSION>' equals required permission
 * @param usageMessage Variable: '<USAGE>' equals command usage
 * @param clientTypeMessage Variable: '<CLIENT>' equals required type of client
 */
data class PluginDesign(
    val messagePrefix: String,
    val permissionMessage: String? = null,
    val usageMessage: String? = null,
    val clientTypeMessage: String? = null,
)
