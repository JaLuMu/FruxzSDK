package de.fruxz.sdk.domain.timer

import de.fruxz.sdk.Main
import de.fruxz.sdk.domain.event.timer.TimerEndEvent
import de.fruxz.sdk.domain.service.SystemService
import de.fruxz.sdk.domain.service.provider.SystemServiceExecutionProvider
import de.fruxz.sdk.domain.service.provider.TickingServiceExecutionProvider
import org.bukkit.scheduler.BukkitRunnable

class TimerProviderService : SystemService {

    override val provider: SystemServiceExecutionProvider
        get() = TickingServiceExecutionProvider(Main.instance, 20, 20, true)
    override val service: BukkitRunnable
        get() = object : BukkitRunnable() {

            override fun run() {

                val managerCompanion = TimerManager
                val out = ArrayList<Timer>()

                managerCompanion.cache.forEach { listTimer ->
                    val remaining = listTimer.remainingSeconds

                    out.add(listTimer.copy(remainingSeconds = remaining-1))

                }

                ArrayList(out)
                    .filter { it.remainingSeconds <= 0 }
                    .forEach {

                        out.remove(it)

                        val event = TimerEndEvent(it.owningPlugin, it)

                        event.owningPlugin.callEvent(event)

                        event.endFunction(it)
                        
                    }

                managerCompanion.cache = out

            }

        }

}