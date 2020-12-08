package de.fruxz.sdk.domain.event.transmission

import de.fruxz.sdk.domain.display.Transmission
import de.fruxz.sdk.kernel.FruxzPlugin
import org.bukkit.event.Cancellable

interface TransmissionEvent : Cancellable {

    val plugin: FruxzPlugin

    val transmission: Transmission

}