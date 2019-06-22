package com.kextor.syntax.textpane


import java.awt.Color
import java.awt.Font
import java.io.File
import javax.swing.BorderFactory
import javax.swing.JTextPane
import javax.swing.SwingUtilities

/**
 * Class that implements a customizable JTextPane and let user
 * to attach it to a tab
 *
 * @author Daniel Córdova A.
 */
class KSyntaxTextArea(
    font: Font,
    initialContext: String = ""
) : JTextPane() {

    init {
        this.font = font
        this.text = initialContext
        this.background = kextorTextAreaBackgroundColor
        this.foreground = kextorTextAreaForegroundColor
        this.caretColor = kextorTextAreaCaretColor
    }

    companion object {
        private var kextorTextAreaBackgroundColor: Color = Color.LIGHT_GRAY
        private var kextorTextAreaForegroundColor: Color = Color.BLACK
        private var kextorTextAreaCaretColor: Color = Color.GRAY
    }
}
