package de.fruxz.sdk.domain.container

enum class InventorySize {

    LARGE, SMALL;

    val size: Int
        get() = when (this) {

            LARGE -> 9*6
            SMALL -> 9*3

        }

    companion object {

        fun sizeOf(size: Int) = when (size) {
            9*3 -> SMALL
            9*6 -> LARGE
            else -> null
        }

    }

}