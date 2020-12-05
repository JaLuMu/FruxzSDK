package de.fruxz.sdk.configuration

import org.bukkit.configuration.file.YamlConfiguration
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import java.io.File
import kotlin.jvm.Throws

/**
 * The [ActiveFileController] interface provides a simple and efficient structure
 * for the management of file data.
 */
interface ActiveFileController {

    /**
     * Represents the active file as a java.io file [File].
     */
    @get:NotNull
    val activeFile: File

    /**
     * Sets the file manager of the [activeFile] for managing the
     * of the [activeFile] as [YamlConfiguration].
     */
    @get:NotNull
    val activeLoader: YamlConfiguration

    /**
     * Loads all active data of a file with its properties
     * into the system via [activeLoader].
     */
    fun load()

    /**
     * Saves all active changed data into the [activeFile] via the [activeLoader]
     * system.
     */
    fun save()

    /**
     * Sets a value of an active parameter under the path [path] to a new value ([newValue] accordingly), which is also evaluated as active element and can correspond to *zero*.
     * This value can be saved with [save] and can be loaded and read (**if not zero**) with [load] & [get].
     */
    fun set(@NotNull path: String, @Nullable newValue: Any?) = set(path = path, newValue = newValue, directInteraction = false)

    /**
     * Sets a value of an active parameter under the path [path] to a new value ([newValue] accordingly), which is also evaluated as active element and can correspond to *zero*.
     * This value can be stored with [save] and can be loaded and read (**if not zero**) with [load] & [get].
     *
     * Read and save is only needed to change this value if [directInteraction] is deactivated (*false*), because if [directInteraction] is activated (i.e. true) the function executes [load] immediately before setting and [save] after setting.
     */
    fun set(@NotNull path: String, @Nullable newValue: Any?, @NotNull directInteraction: Boolean = false)

    /**
     * Returns an active or inactive value from the [activeFile] file or from the active server cache. This value is taken as a [T] value and can throw a [ClassCastException] during the conversion process.
     * The resulting Active or Inactive value can correspond to either [T] or ***null*** in the output if the value does not exist as an active element in the file or cache.
     * **INFO: If ***null*** no exception will be thrown! **
     */
    @Nullable
    @Throws(ClassCastException::class)
    fun <T> get(@NotNull path: String): T? = get(path = path, directInteraction = false)

    /**
     * Returns an active or inactive value from the [activeFile] file or from the active server cache. This value is taken as a [T] value and can throw a [ClassCastException] during the conversion process.
     * The resulting Active or Inactive value can be either [T] or [fallback] in the output if the value does not exist as an active element in the file or cache. This triggers the [fallback] value so that the final result *Never* can be zero.
     * **INFO: If [fallback] == ***null*** an exception is thrown!
     */
    @NotNull
    @Throws(ClassCastException::class)
    fun <T> get(@NotNull path: String, @NotNull fallback: T): T = get(path = path) ?: fallback

    /**
     * Returns an active or inactive value from the [activeFile] file or from the active server cache. This value is taken as a [T] value and can throw a [ClassCastException] during the conversion process.
     * The resulting Active or Inactive value can correspond to either [T] or ***null*** in the output if the value does not exist as an active element in the file or cache.
     *
     * Read explicit is only needed to get this value if [directInteraction] is deactivated (*false*), because if [directInteraction] is activated (i.e. true) the function executes [load] directly before reading.
     *
     * **INFO: If ***null*** no exception will be thrown! **
     */
    @Nullable
    @Throws(ClassCastException::class)
    fun <T> get(@NotNull path: String, @NotNull directInteraction: Boolean = false): T?

    /**
     * Returns an active or inactive value from the [activeFile] file or from the active server cache. This is taken as a [T] value and may throw a [ClassCastException] during the conversion process.
     * The resulting Active or Inactive value can correspond to either [T] or [fallback] in the output if the value does not exist as an active element in the file or cache.
     * This triggers the [fallback] value so that the final result *Never* can equal zero.
     *
     * Explicit read is only needed to get this value if [directInteraction] is deactivated (*false*), because if [directInteraction] is activated (i.e. true) the function executes [load] directly before reading.
     *
     * **INFO: If [fallback] == ***null*** an exception is thrown!
     */
    @NotNull
    @Throws(ClassCastException::class)
    fun <T> get(@NotNull path: String, @NotNull fallback: T, @NotNull directInteraction: Boolean = false): T = get(path = path, directInteraction = directInteraction) ?: fallback

}