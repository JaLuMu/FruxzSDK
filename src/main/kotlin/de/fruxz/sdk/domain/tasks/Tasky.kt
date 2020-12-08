package de.fruxz.sdk.domain.tasks

import de.fruxz.sdk.kernel.FruxzPlugin

/**
 * This interface helps to easily generate tasks everywhere
 */
interface Tasky {

    /**
     * runs an tasks with specified parameters
     * @param plugin The executing plugin
     * @param isAsync The type of execution (async or not?)
     * @param delay The time during code and first execution
     * @param duration The time between one execution and the next execution
     * @param function The code, which executes at the perfect time
     */
    fun task(
        plugin: FruxzPlugin,
        isAsync: Boolean = false,
        delay: Long? = null,
        duration: Long? = null,
        function: (RemoteBukkitTaskController) -> Unit
    ) = RestrictedTaskSingletonBuilder(plugin).task(isAsync = isAsync, delay = delay, duration = duration, function = function)

    /**
     * runs an sync tasks with specified parameters
     * @param plugin The executing plugin
     * @param delay The time during code and first execution
     * @param duration The time between one execution and the next execution
     * @param function The code, which executes at the perfect time
     */
    fun sync(
        plugin: FruxzPlugin,
        delay: Long? = null,
        duration: Long? = null,
        function: (RemoteBukkitTaskController) -> Unit
    ) = RestrictedTaskSingletonBuilder(plugin).sync(delay = delay, duration = duration, function = function)

    /**
     * runs an async tasks with specified parameters
     * @param plugin The executing plugin
     * @param delay The time during code and first execution
     * @param duration The time between one execution and the next execution
     * @param function The code, which executes at the perfect time
     */
    fun async(
        plugin: FruxzPlugin,
        delay: Long? = null,
        duration: Long? = null,
        function: (RemoteBukkitTaskController) -> Unit
    ) = RestrictedTaskSingletonBuilder(plugin).async(delay = delay, duration = duration, function = function)

    /**
     * Stops all controllers specified
     * @param controller Tasks, which will stop
     */
    fun shutdown(
        vararg controller: RemoteBukkitTaskController
    ) {
        controller.forEach {
            it.shutdown()
        }
    }

}