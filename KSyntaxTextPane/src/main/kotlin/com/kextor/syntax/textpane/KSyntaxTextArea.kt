package com.kextor.syntax.textpane

import java.awt.Dimension
import javax.swing.JTextPane
import javax.swing.SwingUtilities
import javax.swing.text.*
import javax.swing.text.View
import javax.swing.text.LabelView




/**
 * Class that implements a customizable JTextPane and let user
 * to attach it to a tab
 *
 * @author Daniel Córdova A.
 */
class KSyntaxTextArea(
    text: String = ""
) : JTextPane() {

    init {
        this.font = KSyntaxTextAreaProperties.textAreaFont
        this.text = text
        this.background = KSyntaxTextAreaProperties.textAreaBackgroundColor
        this.foreground = KSyntaxTextAreaProperties.textAreaForegroundColor
        this.caretColor = KSyntaxTextAreaProperties.caretColor

        SwingUtilities.invokeLater {
            this.requestFocusInWindow()
        }
    }
}
