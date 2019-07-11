package com.kextor.utils

import java.awt.Color

/**
 * Singleton that helps to create a Color only if
 * color property has 4 numbers well formed
 * otherwise will return a "null" Color
 *
 * @author Daniel Córdova A.
 */
object ColorUtility {

    // TODO improve this function and see if it's necessary to take it to another class
    fun getColorProperty(colorProperty: String?): Color? {
        var color: Color? = null
        if (colorProperty != null ) {
            val listOfColorStringNumber: List<String> = colorProperty.replace("\\s+".toRegex(), "").split(",")

            try {
                if (listOfColorStringNumber.size != 4) {
                    throw IllegalArgumentException("Color must have 4 values")
                }

                val mutableListOfColorNumbers: MutableList<Int> = getColorNumbers(listOfColorStringNumber)
                color = createColor(mutableListOfColorNumbers)
            } catch (iae: IllegalArgumentException) {
                System.err.println("Bad color ${iae.message}")
            }
        }
        return color
    }

    private fun getColorNumbers(listOfColorStringNumber: List<String>): MutableList<Int> {
        val mutableListOfColorNumbers: MutableList<Int> = mutableListOf()
        listOfColorStringNumber.forEach {
            try {
                mutableListOfColorNumbers.add(it.toInt())
            } catch (nfe: NumberFormatException) {
                System.err.println("Value is not a number ${nfe.message}")
            }
        }
        return mutableListOfColorNumbers
    }

    private fun createColor(mutableListOfColorNumbers: MutableList<Int>): Color? {
        return if (mutableListOfColorNumbers.size == 4) {
            Color(
                mutableListOfColorNumbers[0],
                mutableListOfColorNumbers[1],
                mutableListOfColorNumbers[2],
                mutableListOfColorNumbers[3]
            )
        } else {
            null
        }
    }
}