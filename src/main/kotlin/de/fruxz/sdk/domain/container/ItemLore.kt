package de.fruxz.sdk.domain.container

import org.bukkit.ChatColor

class ItemLore {

    val content = ArrayList<String>()

    constructor()

    constructor(start: String) {
        content.add(start)
    }

    constructor(content: Collection<String?>?) {

        if (!content.isNullOrEmpty()) {
            content
                .filterNotNull()
                .forEach {

                    this.content.add(it)

                }

        }

    }

    constructor(content: Array<String?>?) {

        if (!content.isNullOrEmpty()) {
            content
                .filterNotNull()
                .forEach {

                    this.content.add(it)

                }

        }

    }

    fun add(content: String, index: Int = this.content.size) {
        this.content.add(index = index, element = content)
    }

    fun add(item: Item, index: Int = this.content.size) {
        this.content.add("${ChatColor.GRAY}[${item.label}${ChatColor.GRAY}]")
    }

    fun addInto(content: String, index: Int = this.content.size) {
        this.content[index] += content
    }

    fun addInto(item: Item, index: Int = this.content.size) {
        this.content[index] += ("${ChatColor.GRAY}[${item.label}${ChatColor.GRAY}]")
    }

    fun remove(index: Int) {
        content.removeAt(index)
    }

    fun clear() {
        content.clear()
    }

}