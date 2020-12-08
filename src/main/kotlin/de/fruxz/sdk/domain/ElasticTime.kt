package de.fruxz.sdk.domain

import kotlin.math.floor
import kotlin.math.roundToInt

/**
 * This class helps to easily convert an time to an displayable clock [String]
 */
class ElasticTime {

    var ticks: Double

    constructor(ticks: Double) {
        this.ticks = ticks
    }

    /**
     * @param format the format of the [timeValue]
     * @param timeValue the value in form of the [format]
     */
    constructor(format: TimeFormat, timeValue: Double) {
        ticks = format.ticks(timeValue)
    }

    /**
     * Generates an clock with the specific display parameters
     * @param views defines, which [TimeFormat]s will be displayed
     * @return the clock
     */
    fun getClockDisplay(vararg views: TimeFormat): String {
        var out = ""

        val remainingYears = floor(ticks / (20*60*60*24*365)).roundToInt()
        val remainingDays = floor(ticks / (20*60*60*24)).roundToInt() - (remainingYears * 365)
        val remainingHours = floor(ticks / (20*60*60)).roundToInt() - (remainingYears * 24*365) - (remainingDays * 24)
        val remainingMinutes = floor(ticks / (20*60)).roundToInt() - (remainingYears * 60*24*365) - (remainingDays * 60*24) - (remainingHours * 60)
        val remainingSeconds = floor(ticks / 20).roundToInt() - (remainingYears * 60*60*24*365) - (remainingDays * 60*60*24) - (remainingHours * 60*60) - (remainingMinutes * 60)
        val remainingTicks = ticks - (remainingYears * 20*60*60*24*365) - (remainingDays * 20*60*60*24) - (remainingHours * 20*60*60) - (remainingMinutes * 20*60) - (remainingSeconds * 20)

        TimeFormat.values()
            .filter { views.contains(it) }
            .forEach { timeFormat ->

                out = when (timeFormat) {

                    TimeFormat.TICKS -> "$remainingTicks".padStart(2, '0')
                    TimeFormat.SECONDS -> "$remainingSeconds".padStart(2, '0')
                    TimeFormat.MINUTES -> "$remainingMinutes".padStart(2, '0')
                    TimeFormat.HOURS -> "$remainingHours".padStart(2, '0')
                    TimeFormat.DAYS -> "$remainingDays".padStart(3, '0')
                    TimeFormat.YEARS -> "$remainingYears".padStart(3, '0')

                } + ":" + out

            }

        return out.removeSuffix(":")
    }

    enum class TimeFormat {
        TICKS, SECONDS, MINUTES, HOURS, DAYS, YEARS;

        /**
         * Converts the TimeFormat-[value] value and convert it into ticks
         * @param value the TimeFormat-value
         * @return value ([TimeFormat]) in ticks
         */
        fun ticks(value: Double) = when (this) {
            TICKS -> value
            SECONDS -> value*20
            MINUTES -> value*60*20
            HOURS -> value*60*60*20
            DAYS -> value*24*60*60*20
            YEARS -> value*365*24*60*60*20
        }

    }


}