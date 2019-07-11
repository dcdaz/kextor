package com.kextor.syntax.textpane.textarea

import com.kextor.syntax.textpane.KSyntaxTextAreaProperties
import javax.swing.JTextArea
import javax.swing.SwingUtilities


/**
 * Class that implements a customizable JTextPane and let user
 * to attach it to a tab
 *
 * @author Daniel Córdova A.
 */
class KSyntaxTextArea(
    text: String = ""
) : JTextArea(text) {

    init {
        this.font = KSyntaxTextAreaProperties.textAreaFont
        this.background = KSyntaxTextAreaProperties.textAreaBackgroundColor
        this.foreground = KSyntaxTextAreaProperties.textAreaForegroundColor
        this.caretColor = KSyntaxTextAreaProperties.caretColor
        this.lineWrap = true
        this.wrapStyleWord = true

        SwingUtilities.invokeLater {
            this.requestFocusInWindow()
        }
    }
}
