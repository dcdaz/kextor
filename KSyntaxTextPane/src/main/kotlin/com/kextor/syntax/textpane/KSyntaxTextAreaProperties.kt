package com.kextor.syntax.textpane

import java.awt.Color
import java.awt.Font

/**
 * Singleton that contains colors for KSyntaxTextArea
 *
 * @author Daniel Córdova A.
 */
object KSyntaxTextAreaProperties {

    // Colors
    var textAreaBackgroundColor: Color = Color.LIGHT_GRAY
    var textAreaForegroundColor: Color = Color.BLACK
    var caretColor: Color = Color.RED

    var gutterBackgroundColor: Color = Color.LIGHT_GRAY
    var gutterLineForegroundColor: Color = Color.BLACK
    var gutterCurrentLineForegroundColor: Color = Color.WHITE

    // Fonts
    var textAreaFont: Font = Font("Default", 0, 14)
    var gutterFont: Font = Font("Default", 0, 12)

}
