package com.kextor.syntax.textpane

import java.awt.Color

/**
 * Singleton that contains colors for KSyntaxTextArea
 *
 * @author Daniel Córdova A.
 */
object KSyntaxTextAreaColors {

    // Default Colors
    private var TEXT_AREA_BACKGROUND: Color = Color.LIGHT_GRAY
    private var TEXT_AREA_FOREGROUND: Color = Color.BLACK
    private var CARET_COLOR: Color = Color.RED
    private var GUTTER_BG_COLOR: Color = Color.LIGHT_GRAY
    private var GUTTER_LINE_COLOR: Color = Color.BLACK
    private var GUTTER_CURRENT_LINE_COLOR: Color = Color.WHITE

    // Mutable Colors
    var textAreaBackgroundColor: Color = TEXT_AREA_BACKGROUND
    var textAreaForegroundColor: Color = TEXT_AREA_FOREGROUND
    var caretColor: Color = CARET_COLOR

    var gutterBackgroundColor: Color = GUTTER_BG_COLOR
    var gutterLineForegroundColor: Color = GUTTER_LINE_COLOR
    var gutterCurrentLineForegroundColor: Color = GUTTER_CURRENT_LINE_COLOR


}
