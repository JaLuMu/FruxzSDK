package de.fruxz.sdk.domain.tasks

/**
 * This interface helps to create an consistent controller
 * for tasks for [Tasky]
 */
interface RemoteBukkitTaskController {

    fun shutdown()

    fun taskID(): Int

    /**
     * May only useful in repeat-task!
     * **0 = not running; 1 = first round; 2 = ...**
     */
    var attempt: Int

}