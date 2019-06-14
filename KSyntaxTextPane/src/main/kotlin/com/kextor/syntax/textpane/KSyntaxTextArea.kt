package com.kextor.syntax.textpane


import com.kextor.ktabs.KTabbedPane
import java.awt.Color
import java.awt.Font
import java.io.File
import javax.swing.BorderFactory
import javax.swing.JScrollPane
import javax.swing.JTextPane
import javax.swing.SwingUtilities

/**
 * Class that implements a customizable JTextPane and let user
 * to attach it to a tab
 *
 * @author Daniel Córdova A.
 */
class KSyntaxTextArea(
    private val textArea: JTextPane,
    var file : File?
) : JScrollPane(textArea) {

    val text : String
        get() = textArea.text
    val code : String
        get() = textArea.document.getText(0, textArea.document.length)

    var title : String
        get() = tabbedPane().getTitleAt(index())
        set(value) {
            tabbedPane().setTitleAt(index(), value)
        }

    private fun tabbedPane() = this.parent as KTabbedPane
    private fun index(): Int = tabbedPane().indexOfComponent(this)

    fun close() {
        tabbedPane().removeTabsFromPaneIndex()
        tabbedPane().removeTabAt(index())
    }

    fun setFontSize(fontSize: Float) {
        super.setFont(super.getFont().deriveFont(fontSize))
        textArea.font = textArea.font.deriveFont(fontSize)
    }

    companion object {

        private var kextorTextAreaBackgroundColor: Color = Color.LIGHT_GRAY
        private var kextorTextAreaForegroundColor: Color = Color.BLACK
        private var kextorTextAreaCaretColor: Color = Color.GRAY

        fun createKextorTextPane(
            font: Font,
            initialContext: String = "",
            file: File? = null
        ): KSyntaxTextArea {
            val kextorTextArea = JTextPane()
            kextorTextArea.font = font
            kextorTextArea.text = initialContext
            kextorTextArea.background = kextorTextAreaBackgroundColor
            kextorTextArea.foreground = kextorTextAreaForegroundColor
            kextorTextArea.caretColor = kextorTextAreaCaretColor

            val gutter = Gutter(kextorTextArea)
            val textPanel = KSyntaxTextArea(kextorTextArea, file)
            textPanel.viewportBorder = BorderFactory.createEmptyBorder()
            textPanel.setRowHeaderView(gutter)
            SwingUtilities.invokeLater {
                kextorTextArea.requestFocusInWindow()
            }
            return textPanel
        }
    }
}
