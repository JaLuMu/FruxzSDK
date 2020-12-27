package de.fruxz.sdk.domain.timer

import de.fruxz.sdk.kernel.FruxzPlugin
import java.util.*

data class Timer(
    val owningPlugin: FruxzPlugin,
    val uniqueIdentity: UUID = UUID.randomUUID(),
    val delayingSeconds: Int,
    var remainingSeconds: Int = delayingSeconds,
    val onEnd: (Timer) -> (Unit)
)