package de.fruxz.sdk.domain.event.timer

import de.fruxz.sdk.domain.timer.Timer
import de.fruxz.sdk.kernel.FruxzPlugin

interface TimerEvent {

    val owningPlugin: FruxzPlugin

    val timer: Timer

}