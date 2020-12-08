package de.fruxz.sdk.domain.display

import net.md_5.bungee.api.chat.TextComponent

interface TransmissionContentObjectable {

    fun getObjectable(): TextComponent

}