package de.fruxz.sdk.domain

import org.bukkit.World

enum class WeatherState {

    SUNNY, RAINY, THUNDY;

    fun setup(world: World) {

        when (this) {
            SUNNY -> {
                world.setStorm(false)
            }
            RAINY -> {
                world.setStorm(true)
                world.isThundering = false
            }
            THUNDY -> {
                world.setStorm(true)
                world.isThundering = true
            }
        }

    }

    companion object {

        fun get(world: World) = when {

            !world.hasStorm() -> {
                SUNNY
            }
            world.hasStorm() && !world.isThundering -> {
                RAINY
            }
            else -> THUNDY

        }

        fun setup(world: World, state: WeatherState) {

            when (state) {
                SUNNY -> {
                    world.setStorm(false)
                }
                RAINY -> {
                    world.setStorm(true)
                    world.isThundering = false
                }
                THUNDY -> {
                    world.setStorm(true)
                    world.isThundering = true
                }
            }

        }

    }

}