package de.fruxz.sdk.domain.container

enum class UIBorder {

    FULL, FLATTEN, SQUASHED, CORNER, DOTTED;

    fun getItemPositions(layout: InventorySize): Array<Int> {
        val out = ArrayList<Int>()

        when (this) {

            FULL -> {
                if (layout == InventorySize.SMALL) {
                    out.addAll(arrayOf(
                        0,1,2,3,4,5,6,7,8,17,26,25,24,23,22,21,20,19,18,9
                    ))
                } else
                    out.addAll(arrayOf(
                        0,1,2,3,4,5,6,7,8,17,26,35,44,53,52,51,50,49,48,47,46,45,36,27,18,9
                    ))
            }
            FLATTEN -> {
                if (layout == InventorySize.SMALL) {
                    out.addAll(arrayOf(
                        0,1,2,3,4,5,6,7,8,18,19,20,21,22,23,24,25,26
                    ))
                } else
                    out.addAll(arrayOf(
                        0,1,2,3,4,5,6,7,8,45,46,47,48,49,50,51,52,53
                    ))
            }
            SQUASHED -> {
                if (layout == InventorySize.SMALL) {
                    out.addAll(arrayOf(
                        0,8,9,17,18,26
                    ))
                } else
                    out.addAll(arrayOf(
                        0,8,9,17,18,26,27,35,36,44,45,53
                    ))
            }
            CORNER -> {
                if (layout == InventorySize.SMALL) {
                    out.addAll(arrayOf(
                        0,1,7,8,17,26,25,19,18,9
                    ))
                } else
                    out.addAll(arrayOf(
                        0,1,7,8,17,44,53,52,46,45,36,9
                    ))
            }
            DOTTED -> {
                if (layout == InventorySize.SMALL) {
                    out.addAll(arrayOf(
                        0,2,4,6,8,26,24,22,20,18
                    ))
                } else
                    out.addAll(arrayOf(
                        0,2,4,6,8,26,44,52,50,48,46,36,18
                    ))
            }

        }

        return out.toTypedArray()
    }

}