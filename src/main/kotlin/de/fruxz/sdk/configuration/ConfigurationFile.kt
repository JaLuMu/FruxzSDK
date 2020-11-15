package de.fruxz.sdk.configuration

import java.io.File
import java.io.FileNotFoundException

/**
 * This is a standard file controller that runs over the [ActiveFileController] interface
 */
class ConfigurationFile(file: File) : ActiveFileController {

    override val activeFile = file

    override fun load() {
        try {
            activeLoader.load(activeFile)
        } catch (e: FileNotFoundException) {
            activeLoader.set("installed", true)
            save()
        }
    }

    override fun save() {
        activeLoader.save(activeFile)
    }

    override fun set(path: String, newValue: Any?, directInteraction: Boolean) {

        if (directInteraction)
            load()

        activeLoader.set(path, newValue)

        if (directInteraction)
            save()

    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> get(path: String, directInteraction: Boolean): T? {

        if (directInteraction)
            load()

        return activeLoader.get(path) as T?

    }

}