package de.fruxz.sdk.util

/**
 * This class helps to manage Lists, collections and general 'containers'
 */
class ListUtils {

    /**
     * This converts an collection ([input]) of [I] to an ArrayList of [O] using
     * the instructions of [process] which has an Input of [I] and returns [O]
     */
    fun <I, O> convert(input: Collection<I>, process: (I) -> O): ArrayList<O> {
        val out = ArrayList<O>()

        input.forEach { out.add(process(it)) }

        return out
    }

    /**
     * This converts an array ([input]) of [I] to an array of [O] using
     * the instructions of [process] which has an Input of [I] and returns [O]
     */
    inline fun <I, reified O> convert(input: Array<I>, noinline process: (I) -> O): Array<out O> = convert(input.toList(), process).toTypedArray()

}