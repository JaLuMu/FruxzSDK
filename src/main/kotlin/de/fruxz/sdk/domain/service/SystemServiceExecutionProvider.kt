package de.fruxz.sdk.domain.service

import org.bukkit.scheduler.BukkitRunnable

interface SystemServiceExecutionProvider {

    val isAsync: Boolean

    fun execute(runnable: BukkitRunnable)

}