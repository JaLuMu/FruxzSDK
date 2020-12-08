package de.fruxz.sdk.domain.service.provider

import de.fruxz.sdk.kernel.FruxzPlugin
import org.bukkit.scheduler.BukkitRunnable

class DelayedServiceExecutionProvider(val plugin: FruxzPlugin, val delay: Long, override val isAsync: Boolean = false) :
    SystemServiceExecutionProvider {

    override fun execute(runnable: BukkitRunnable) {
        when (isAsync) {
            true -> runnable.runTaskLaterAsynchronously(plugin, delay)
            false -> runnable.runTaskLater(plugin, delay)
        }
    }

}