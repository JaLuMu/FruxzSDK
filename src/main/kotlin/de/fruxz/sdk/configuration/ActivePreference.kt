package de.fruxz.sdk.configuration

import org.jetbrains.annotations.NotNull

/**
 * This class helps to easily read&write data to&from an [ActiveFileController] (File).
 * @param activeFile The file, which is used for read & write
 * @param path The path, which the data is saved
 * @param default The default object, which is defined for [path]
 * @param useCache Save the object in the system-memory
 */
open class ActivePreference<T>(
    val activeFile: ActiveFileController,
    val path: String,
    val default: T,
    val useCache: Boolean = false
) {

    companion object {

        /**
         * The system-memory for [useCache] enabled objects
         * *saved under [getSuperPath]!*
         */
        val cache = HashMap<String, Any>()

    }

    /**
     * Generates an superpath used by the [cache]
     */
    fun getSuperPath() = "${activeFile.activeFile.path}::$path"

    /**
     * Returns the current saved (or memory saved) object
     */
    fun getContent(): T {
        val v = cache[getSuperPath()]

        if (!useCache && v != null) {
            return try {
                v as T
            } catch (e: ClassCastException) {
                setContent(default)
                default
            }
        } else {
            val e = activeFile.get<T>(path)

            return if (activeFile.activeLoader.contains(path) && e != null) {

                if (useCache)
                    cache[getSuperPath()] = e

                e
            } else {

                activeFile.set(path, default)

                if (useCache)
                    cache[getSuperPath()] = default!!

                default
            }

        }

    }

    /**
     * Replaces the currently saved (or memory saved) object with an new object ([value])
     */
    fun setContent(@NotNull value: T) {
        try {

            if (useCache)
                cache[getSuperPath()] = value!!

            activeFile.set(path = path, newValue = value)

        } catch (ignore: StringIndexOutOfBoundsException) {
        }
    }

}