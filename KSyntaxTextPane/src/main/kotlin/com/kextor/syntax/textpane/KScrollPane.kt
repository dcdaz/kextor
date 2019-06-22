package com.kextor.syntax.textpane

import com.kextor.ktabs.KTabbedPane
import java.io.File
import javax.swing.BorderFactory
import javax.swing.JScrollPane
import javax.swing.SwingUtilities

class KScrollPane(
    private val textArea: KSyntaxTextArea,
    var file : File?
): JScrollPane(textArea) {

    init {
        val gutter = Gutter(textArea)
        this.viewportBorder = BorderFactory.createEmptyBorder()
        this.setRowHeaderView(gutter)

        SwingUtilities.invokeLater {
            this.requestFocusInWindow()
        }
    }

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
}