package com.kextor

import com.kextor.syntax.textpane.KSyntaxTextArea
import com.kextor.ktabs.KTabbedPane
import com.kextor.syntax.textpane.KScrollPane
import java.awt.Color
import java.awt.Font
import java.io.File
import javax.swing.JOptionPane

/**
 * Class that handles SyntaxtTextPanes instances and
 * add it or remove it from tabs
 *
 * @author Daniel Córdova A.
 */
class KextorTabsHandler(kextorTabbedPane: KTabbedPane) {

    companion object {
        private const val DEFAULT_FONT_SIZE = 12.0f
        private val BACKGROUND_DARKER = Color(23, 24, 20)
    }

    private val currentTab : KScrollPane?
        get() = tabbedPane.selectedComponent as KScrollPane

    val currentCode : String?
        get() = this.currentTab?.code

    private val defaultFont: Font = Font("Default", 0, 14)

    var fontSize : Float = DEFAULT_FONT_SIZE
        set(value) {
            field = value
            currentTab?.setFontSize(value)
        }

    private val tabbedPane: KTabbedPane = kextorTabbedPane

    fun addTab(
        title: String,
        font: Font = defaultFont,
        initialContext: String = "",
        file: File? = null) {

        try {
            val kSyntaxTextArea = KSyntaxTextArea(
                font,
                initialContext
            )
            val kScrollPane = KScrollPane(kSyntaxTextArea, file)
            val fullTitle = "<html><p style='font-size:small;'>$title</p></html>"
            tabbedPane.addTab(
                fullTitle,
                null,
                kScrollPane,
                "Go to $title"
            )
            tabbedPane.setForegroundAt(tabbedPane.tabCount - 1, Color.white)
            tabbedPane.setBackgroundAt(tabbedPane.tabCount - 1, BACKGROUND_DARKER)
            tabbedPane.addTabsToPaneIndex()
        } catch (e : Exception) {
            JOptionPane.showMessageDialog(tabbedPane, "Error creating tab: ${e.message}")
            e.printStackTrace()
        }
    }
}
