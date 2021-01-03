package de.fruxz.sdk.domain.event.entity.player.interact

interface PlayerInteract {

    /**
     * returns if the event-action is denied or not, null if default
     */
    var isDenied: Boolean?

}