package de.fruxz.sdk.domain.tasks

import de.fruxz.sdk.kernel.FruxzPlugin
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

/**
 * This class helps to build/manage and structure tasks, which will run
 * for itself and with its own controller-consoles
 */
class RestrictedTaskSingletonBuilder(val plugin: FruxzPlugin) {

    /**
     * Generates an task
     * @param isAsync Is the task async or not?
     * @param delay The time between code an first execution
     * @param duration The time between one execution and the next execution
     * @param function The code, which will run at the perfect time
     */
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

    /**
     * Generates an sync task
     * @param delay The time between code an first execution
     * @param duration The time between one execution and the next execution
     * @param function The code, which will run at the perfect time
     */
    fun sync(
        delay: Long? = null,
        duration: Long? = null,
        function: (RemoteBukkitTaskController) -> Unit
    ) = task(isAsync = false, delay = delay, duration = duration, function = function)

    /**
     * Generates an async task
     * @param delay The time between code an first execution
     * @param duration The time between one execution and the next execution
     * @param function The code, which will run at the perfect time
     */
    fun async(
        delay: Long? = null,
        duration: Long? = null,
        function: (RemoteBukkitTaskController) -> Unit
    ) = task(isAsync = true, delay = delay, duration = duration, function = function)

}