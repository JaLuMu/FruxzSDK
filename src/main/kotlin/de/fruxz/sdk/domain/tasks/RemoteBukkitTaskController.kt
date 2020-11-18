package de.fruxz.sdk.domain.tasks

interface RemoteBukkitTaskController {

    fun shutdown()

    fun taskID(): Int

    /**
     * May is only useful in repeat-task!
     * **0 = not running; 1 = first round; 2 = ...**
     */
    var attempt: Int

}