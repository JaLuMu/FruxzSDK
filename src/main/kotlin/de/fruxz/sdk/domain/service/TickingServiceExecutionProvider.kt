package de.fruxz.sdk.domain.service

import de.fruxz.sdk.kernel.FruxzPlugin
import org.bukkit.scheduler.BukkitRunnable

class TickingServiceExecutionProvider(val plugin: FruxzPlugin, val delay: Long, val waiting: Long, override val isAsync: Boolean = false) : SystemServiceExecutionProvider {

    override fun execute(runnable: BukkitRunnable) {
        when (isAsync) {
            true -> runnable.runTaskTimerAsynchronously(plugin, delay, waiting)
            false -> runnable.runTaskTimer(plugin, delay, waiting)
        }
    }

}