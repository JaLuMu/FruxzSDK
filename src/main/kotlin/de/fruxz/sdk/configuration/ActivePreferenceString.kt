package de.fruxz.sdk.configuration

/**
 * This helps to easily write&save string preferences, which may contain color & color-codes
 */
class ActivePreferenceString(
    activeFile: ActiveFileController,
    path: String,
    default: String,
    useCache: Boolean = false
) : ActivePreference<String>(activeFile, path, default.replace("§", "//COLOR:"), useCache) {

    fun getMessage() = getContent().replace("//COLOR:", "§")

    fun getMessage(vararg replacor: Pair<String, String>): String {
        var out = getMessage()

        replacor.forEach {
            out = out.replace(it.first, it.second)
        }

        return out
    }

}