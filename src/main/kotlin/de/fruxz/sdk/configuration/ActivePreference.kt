package de.fruxz.sdk.configuration

import org.jetbrains.annotations.NotNull

open class ActivePreference<T>(
    val activeFile: ActiveFileController,
    val path: String,
    val default: T,
    val useCache: Boolean = false
) {

    companion object {
        val cache = HashMap<String, Any>()
    }

    fun getSuperPath() = "${activeFile.activeFile.path}::$path"

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

    fun setContent(@NotNull value: T) {
        try {

            if (useCache)
                cache[getSuperPath()] = value!!

            activeFile.set(path = path, newValue = value)

        } catch (ignore: StringIndexOutOfBoundsException) {
        }
    }

}