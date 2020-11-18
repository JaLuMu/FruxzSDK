package de.fruxz.sdk.domain.service

import org.bukkit.scheduler.BukkitRunnable

interface SystemService {

    val provider: SystemServiceExecutionProvider

    val service: BukkitRunnable

    fun boot() {

        provider.execute(service)

    }

}