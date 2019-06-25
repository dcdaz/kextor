package com.kextor

import com.kextor.syntax.textpane.KSyntaxTextAreaProperties
import com.kextor.ui.KextorSplashScreen
import com.kextor.utils.PropertyManager
import java.awt.Color
import java.awt.Font
import javax.swing.SwingUtilities

/**
 * Main Class of Kextor
 *
 * @author Daniel Córdova A.
 */
object Kextor {

    val propertyManager = PropertyManager()
    private const val PROPERTIES_EXTENSION = "properties"

    @JvmStatic
    fun main(args: Array<String>) {
        loadAppProperties("Kextor") // Load Properties first

        val iconTheme: String? = Kextor.propertyManager.getProperty("icon.theme")

        addKextorSplashScreen(iconTheme)

        val kextor = KextorInitGUI()
        val backgroundColor: Color = getColorProperty(propertyManager.getProperty("color.kextor.background")!!)

        // Text Area Properties
        setKSyntaxTextAreaFont()
        setGutterFont()
        setKSyntaxTexAreaColors()
        setGutterColors()
        setScrollbarColors()

        kextor.setBackgroundColor(backgroundColor)
        SwingUtilities.invokeLater { kextor.createAndShowKextorGUI(iconTheme) }
    }

    private fun loadAppProperties(resourceName: String) {
        propertyManager.loadAppProps(
            this::class.java.getResourceAsStream("/$resourceName.$PROPERTIES_EXTENSION")
        )
    }

    // TODO improve this function and see if it's necessary to take it to another class
    private fun getColorProperty(colorProperty: String): Color {
        val listOfColorStringNumber: List<String> = colorProperty.replace("\\s+".toRegex(), "").split(",")
        val mutableListOfColorNumbers: MutableList<Int> = mutableListOf()

        if(listOfColorStringNumber.size != 4) {
            throw IllegalArgumentException("Color must have 4 numbers")
        }

        listOfColorStringNumber.forEach {
            try {
                mutableListOfColorNumbers.add(it.toInt())
            } catch (e: NumberFormatException) {
                System.err.println("Value is not a number ${e.message}")
            }
        }

        return Color(
            mutableListOfColorNumbers[0],
            mutableListOfColorNumbers[1],
            mutableListOfColorNumbers[2],
            mutableListOfColorNumbers[3]
        )
    }

    private fun addKextorSplashScreen(iconTheme: String?) {
        val showSplashScreen: Boolean = Kextor.propertyManager.getProperty("splash.show")!!.toBoolean()

        if (showSplashScreen) {
            val splashTheme: String? = Kextor.propertyManager.getProperty("splash.theme")
            KextorSplashScreen(splashTheme, iconTheme)
        }
    }

    private fun setKSyntaxTexAreaColors() {
        val textAreaBGColor: Color = getColorProperty(propertyManager.getProperty("color.textarea.background")!!)
        val textAreaFGColor: Color = getColorProperty(propertyManager.getProperty("color.textarea.foreground")!!)
        val caretColor: Color = getColorProperty(propertyManager.getProperty("color.textarea.caret")!!)

        KSyntaxTextAreaProperties.textAreaBackgroundColor = textAreaBGColor
        KSyntaxTextAreaProperties.textAreaForegroundColor = textAreaFGColor
        KSyntaxTextAreaProperties.caretColor = caretColor
    }

    private fun setGutterColors() {
        val gutterBGColor: Color = getColorProperty(propertyManager.getProperty("color.gutter.background")!!)
        val gutterLineColor: Color = getColorProperty(propertyManager.getProperty("color.gutter.foreground")!!)
        val gutterCurrentLineColor: Color = getColorProperty(
            propertyManager.getProperty("color.gutter.currentLineForeground")!!
        )

        KSyntaxTextAreaProperties.gutterBackgroundColor = gutterBGColor
        KSyntaxTextAreaProperties.gutterLineForegroundColor = gutterLineColor
        KSyntaxTextAreaProperties.gutterCurrentLineForegroundColor = gutterCurrentLineColor
    }

    private fun setKSyntaxTextAreaFont() {
        val fontSize: Int = propertyManager.getProperty("font.textarea.size")!!.toInt()
        val fontName: String = propertyManager.getProperty("font.textarea.name")!!
        val fontStyle: Int = propertyManager.getProperty("font.textarea.style")!!.toInt()

        val kSyntaxTextAreaFont = Font(fontName, fontStyle, fontSize)
        KSyntaxTextAreaProperties.textAreaFont = kSyntaxTextAreaFont
    }

    private fun setGutterFont() {
        val fontSize: Int = propertyManager.getProperty("font.gutter.size")!!.toInt()
        val fontName: String = propertyManager.getProperty("font.gutter.name")!!
        val fontStyle: Int = propertyManager.getProperty("font.gutter.style")!!.toInt()

        val kSyntaxTextAreaFont = Font(fontName, fontStyle, fontSize)
        KSyntaxTextAreaProperties.gutterFont = kSyntaxTextAreaFont
    }

    private fun setScrollbarColors() {
        val scrollbarBorderColor: Color = getColorProperty(propertyManager.getProperty("color.scrollbar.border")!!)
        val scrollbarHoverColor: Color = getColorProperty(propertyManager.getProperty("color.scrollbar.hover")!!)
        val scrollbarDraggingColor: Color = getColorProperty(propertyManager.getProperty("color.scrollbar.dragging")!!)
        val scrollbarBodyColor: Color = getColorProperty(propertyManager.getProperty("color.scrollbar.body")!!)

        KSyntaxTextAreaProperties.kScrollBarBorderColor = scrollbarBorderColor
        KSyntaxTextAreaProperties.kScrollBarHoverColor = scrollbarHoverColor
        KSyntaxTextAreaProperties.kScrollBarDraggingColor = scrollbarDraggingColor
        KSyntaxTextAreaProperties.kScrollBarBodyColor = scrollbarBodyColor
    }

}
