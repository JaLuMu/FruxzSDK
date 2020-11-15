package de.fruxz.sdk.util

class ListUtils {

    fun <I, O> convert(input: Collection<I>, process: (I) -> O): ArrayList<O> {
        val out = ArrayList<O>()

        input.forEach { out.add(process(it)) }
        convert(arrayOf(1, 2, 3)) {
            it.toString()
        }

        return out
    }

    inline fun <I, reified O> convert(input: Array<I>, noinline process: (I) -> O): Array<out O> = convert(input.toList(), process).toTypedArray()

}