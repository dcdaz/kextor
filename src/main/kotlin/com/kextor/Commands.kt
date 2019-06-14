package com.kextor

import com.kextor.syntax.textpane.KSyntaxTextArea
import com.kextor.ktabs.KTabbedPane
import java.nio.charset.Charset
import javax.swing.JFileChooser

/**
 * Singleton with commands that will be executed on Kextor
 *
 * @author Daniel Córdova A.
 */
object Commands {

    internal fun openCommand(tabbedPane: KTabbedPane) {
        val fc = JFileChooser()
        val res: Int = fc.showOpenDialog(tabbedPane)
        if (res == JFileChooser.APPROVE_OPTION) {
            KextorTabsHandler(tabbedPane).addTab(
                fc.selectedFile.name,
                initialContext = fc.selectedFile.readText(Charset.defaultCharset()),
                file= fc.selectedFile
            )
        }
    }

    internal fun newCommand(tabbedPane: KTabbedPane) {
        KextorTabsHandler(tabbedPane).addTab("Untitled ~")
    }

    internal fun saveAsCommand(tabbedPane : KTabbedPane) {
        if (tabbedPane.selectedComponent == null) {
            return
        }
        val fc = JFileChooser()
        val res = fc.showSaveDialog(tabbedPane)
        if (res == JFileChooser.APPROVE_OPTION) {
            (tabbedPane.selectedComponent as KSyntaxTextArea).file = fc.selectedFile
            fc.selectedFile.writeText((tabbedPane.selectedComponent as KSyntaxTextArea).text)
            (tabbedPane.selectedComponent as KSyntaxTextArea).title = fc.selectedFile.name
        }
    }

    internal fun saveCommand(tabbedPane : KTabbedPane) {
        if (tabbedPane.selectedComponent == null) {
            return
        }
        val file = (tabbedPane.selectedComponent as KSyntaxTextArea).file
        if (file == null) {
            saveAsCommand(tabbedPane)
        } else {
            file.writeText((tabbedPane.selectedComponent as KSyntaxTextArea).text)
        }
    }

    internal fun closeCommand(tabbedPane : KTabbedPane) {
        if (tabbedPane.selectedComponent == null) {
            return
        }
        (tabbedPane.selectedComponent as KSyntaxTextArea).close()
    }
}
