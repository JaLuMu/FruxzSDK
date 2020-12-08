package de.fruxz.sdk.handler

import de.fruxz.sdk.Main
import de.fruxz.sdk.domain.WeatherState
import de.fruxz.sdk.domain.event.world.WeatherStateUpdateEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.weather.WeatherChangeEvent

class WeatherHandler : Listener {

    @EventHandler
    fun onWeather(event: WeatherChangeEvent) {
        val stateEvent = WeatherStateUpdateEvent(event.world, WeatherState.get(event.world), false)

        Main.instance.callEvent(stateEvent)

        event.isCancelled = stateEvent.isCancelled

    }

}