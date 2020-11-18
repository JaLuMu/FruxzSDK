package de.fruxz.sdk.domain.tasks

import de.fruxz.sdk.kernel.FruxzPlugin
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

class RestrictedTaskSingletonBuilder(val plugin: FruxzPlugin) {

    fun task(
        isAsync: Boolean = false,
        delay: Long? = null,
        duration: Long? = null,
        function: (RemoteBukkitTaskController) -> Unit
    ) {

        val task =
            object : BukkitRunnable() {

                private var currentAttempt = 0

                override fun run() {
                    currentAttempt++

                    function(

                        object : RemoteBukkitTaskController {
                            override fun shutdown() {
                                Bukkit.getScheduler().cancelTask(taskID())
                                cancel()
                            }

                            override fun taskID(): Int {
                                return taskId
                            }

                            override var attempt: Int
                                get() = currentAttempt
                                set(value) {currentAttempt = value}

                        }

                    )

                }

            }

        if (delay != null) {
            if (duration != null) {
                if (isAsync) {
                    task.runTaskTimerAsynchronously(
                        plugin,
                        delay,
                        duration
                    )
                } else {
                    task.runTaskTimer(
                        plugin,
                        delay,
                        duration
                    )
                }
            } else {
                if (isAsync) {
                    task.runTaskLaterAsynchronously(
                        plugin,
                        delay
                    )
                } else {
                    task.runTaskLater(
                        plugin,
                        delay
                    )
                }
            }
        } else {
            if (isAsync) {
                task.runTaskAsynchronously(
                    plugin
                )
            } else {
                task.runTask(
                    plugin
                )
            }

        }
    }

    fun sync(
        delay: Long? = null,
        duration: Long? = null,
        function: (RemoteBukkitTaskController) -> Unit
    ) = task(isAsync = false, delay = delay, duration = duration, function = function)

    fun async(
        delay: Long? = null,
        duration: Long? = null,
        function: (RemoteBukkitTaskController) -> Unit
    ) = task(isAsync = true, delay = delay, duration = duration, function = function)

}