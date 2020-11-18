package de.fruxz.sdk.domain.tasks

import de.fruxz.sdk.kernel.FruxzPlugin

interface Tasky {

    fun task(
        plugin: FruxzPlugin,
        isAsync: Boolean = false,
        delay: Long? = null,
        duration: Long? = null,
        function: (RemoteBukkitTaskController) -> Unit
    ) = RestrictedTaskSingletonBuilder(plugin).task(isAsync = isAsync, delay = delay, duration = duration, function = function)

    fun sync(
        plugin: FruxzPlugin,
        delay: Long? = null,
        duration: Long? = null,
        function: (RemoteBukkitTaskController) -> Unit
    ) = RestrictedTaskSingletonBuilder(plugin).sync(delay = delay, duration = duration, function = function)

    fun async(
        plugin: FruxzPlugin,
        delay: Long? = null,
        duration: Long? = null,
        function: (RemoteBukkitTaskController) -> Unit
    ) = RestrictedTaskSingletonBuilder(plugin).async(delay = delay, duration = duration, function = function)

    fun shutdown(
        vararg controller: RemoteBukkitTaskController
    ) {
        controller.forEach {
            it.shutdown()
        }
    }

}