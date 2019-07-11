package com.kextor

import com.kextor.syntax.textpane.KSyntaxTextAreaProperties
import com.kextor.ui.KextorSplashScreen
import com.kextor.utils.PropertyManager
import java.awt.Color
import java.awt.Font
import javax.swing.SwingUtilities
import com.kextor.utils.ColorUtility

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
        val backgroundColor: Color? = ColorUtility.getColorProperty(
            propertyManager.getProperty("color.kextor.background")
        )

        // Text Area Properties
        setKSyntaxTextAreaFont()
        setGutterFont()
        setKSyntaxTexAreaColors()
        setGutterColors()
        setScrollbarColors()

        backgroundColor?.let {
            kextor.setBackgroundColor(it)
        }
        SwingUtilities.invokeLater { kextor.createAndShowKextorGUI(iconTheme) }
    }

    private fun loadAppProperties(resourceName: String) {
        propertyManager.loadAppProps(
            this::class.java.getResourceAsStream("/$resourceName.$PROPERTIES_EXTENSION")
        )
    }

    private fun addKextorSplashScreen(iconTheme: String?) {
        val showSplashScreen: Boolean? = Kextor.propertyManager.getProperty("splash.show")?.toBoolean()

        if (showSplashScreen == true) {
            val splashTheme: String? = Kextor.propertyManager.getProperty("splash.theme")
            KextorSplashScreen(splashTheme, iconTheme)
        }
    }

    private fun setKSyntaxTexAreaColors() {
        val textAreaBGColor: Color? = ColorUtility.getColorProperty(
            propertyManager.getProperty("color.textarea.background")
        )
        val textAreaFGColor: Color? = ColorUtility.getColorProperty(
            propertyManager.getProperty("color.textarea.foreground")
        )
        val caretColor: Color? = ColorUtility.getColorProperty(
            propertyManager.getProperty("color.textarea.caret")
        )

        textAreaBGColor?.let{ KSyntaxTextAreaProperties.textAreaBackgroundColor = it }
        textAreaFGColor?.let { KSyntaxTextAreaProperties.textAreaForegroundColor = it }
        caretColor?.let { KSyntaxTextAreaProperties.caretColor = it }
    }

    private fun setGutterColors() {
        val gutterBGColor: Color? = ColorUtility.getColorProperty(
            propertyManager.getProperty("color.gutter.background")
        )
        val gutterLineColor: Color? = ColorUtility.getColorProperty(
            propertyManager.getProperty("color.gutter.foreground")
        )
        val gutterCurrentLineColor: Color? = ColorUtility.getColorProperty(
            propertyManager.getProperty("color.gutter.currentLineForeground")
        )

        gutterBGColor?.let { KSyntaxTextAreaProperties.gutterBackgroundColor = it }
        gutterLineColor?.let { KSyntaxTextAreaProperties.gutterLineForegroundColor = it }
        gutterCurrentLineColor?.let { KSyntaxTextAreaProperties.gutterCurrentLineForegroundColor = it }
    }

    private fun setKSyntaxTextAreaFont() {
        val fontSize: Int? = propertyManager.getProperty("font.textarea.size")?.toInt()
        val fontName: String? = propertyManager.getProperty("font.textarea.name")
        val fontStyle: Int? = propertyManager.getProperty("font.textarea.style")?.toInt()
        var kSyntaxTextAreaFont: Font? = null
        fontSize?.let { size ->
            fontStyle?.let {
                kSyntaxTextAreaFont = Font(fontName, it, size)
            }
        }

        kSyntaxTextAreaFont?.let {
            KSyntaxTextAreaProperties.textAreaFont = it
        }
    }

    private fun setGutterFont() {
        val fontSize: Int? = propertyManager.getProperty("font.gutter.size")?.toInt()
        val fontName: String? = propertyManager.getProperty("font.gutter.name")
        val fontStyle: Int? = propertyManager.getProperty("font.gutter.style")?.toInt()
        var kSyntaxTextAreaGutterFont: Font? = null
        fontSize?.let { size ->
            fontStyle?.let {
                kSyntaxTextAreaGutterFont = Font(fontName, it, size)
            }
        }

        kSyntaxTextAreaGutterFont?.let {
            KSyntaxTextAreaProperties.gutterFont = it
        }
    }

    private fun setScrollbarColors() {
        val scrollbarBorderColor: Color? = ColorUtility.getColorProperty(
            propertyManager.getProperty("color.scrollbar.border")
        )
        val scrollbarHoverColor: Color? = ColorUtility.getColorProperty(
            propertyManager.getProperty("color.scrollbar.hover")
        )
        val scrollbarDraggingColor: Color? = ColorUtility.getColorProperty(
            propertyManager.getProperty("color.scrollbar.dragging")
        )
        val scrollbarBodyColor: Color? = ColorUtility.getColorProperty(
            propertyManager.getProperty("color.scrollbar.body")
        )

        scrollbarBorderColor?.let { KSyntaxTextAreaProperties.kScrollBarBorderColor = it }
        scrollbarHoverColor?.let { KSyntaxTextAreaProperties.kScrollBarHoverColor = it }
        scrollbarDraggingColor?.let {KSyntaxTextAreaProperties.kScrollBarDraggingColor = it }
        scrollbarBodyColor?.let { KSyntaxTextAreaProperties.kScrollBarBodyColor = it }
    }

}
