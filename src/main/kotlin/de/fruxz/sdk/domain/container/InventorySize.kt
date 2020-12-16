package de.fruxz.sdk.domain.container

/**
 * This class helps to easily become one of the minecraft-support inventory sizes
 *
 * WIKI: [Object: InventorySize (ENUM)](https://github.com/TheFruxz/FruxzSDK/wiki/Object:-InventorySize-(ENUM))
 */
enum class InventorySize {

    LARGE, SMALL;

    /**
     * This is the value of slots, which the inventory-size [InventorySize] contains,
     * if it is in an inventory
     */
    val size: Int
        get() = when (this) {

            LARGE -> 9*6
            SMALL -> 9*3

        }

    companion object {

        /**
         * Returns the [InventorySize] object, which can have an equal number of inventory slots ([size]).
         * If none can be found, null is returned!
         */
        fun sizeOf(size: Int) = when (size) {
            9*3 -> SMALL
            9*6 -> LARGE
            else -> null
        }

    }

}