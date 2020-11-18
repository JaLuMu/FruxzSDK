package de.fruxz.sdk.domain.service

import de.fruxz.sdk.kernel.FruxzPlugin
import org.bukkit.scheduler.BukkitRunnable

class InstantServiceExecutionProvider(val plugin: FruxzPlugin, override val isAsync: Boolean = false) : SystemServiceExecutionProvider {

    override fun execute(runnable: BukkitRunnable) {
        when (isAsync) {
            true -> runnable.runTaskAsynchronously(plugin)
            false -> runnable.runTask(plugin)
        }
    }

}