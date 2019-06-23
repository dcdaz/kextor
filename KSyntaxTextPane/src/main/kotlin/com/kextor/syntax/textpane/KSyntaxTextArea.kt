package com.kextor.syntax.textpane


import java.awt.Font
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
    text: String = ""
) : JTextPane() {

    init {
        this.font = font
        this.text = text
        this.background = KSyntaxTextAreaColors.textAreaBackgroundColor
        this.foreground = KSyntaxTextAreaColors.textAreaForegroundColor
        this.caretColor = KSyntaxTextAreaColors.caretColor

        SwingUtilities.invokeLater {
            this.requestFocusInWindow()
        }
    }
}
