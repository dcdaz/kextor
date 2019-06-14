package com.kextor

import com.kextor.Commands.closeCommand
import com.kextor.Commands.newCommand
import com.kextor.Commands.openCommand
import com.kextor.Commands.saveAsCommand
import com.kextor.Commands.saveCommand
import com.kextor.kmenu.KMenu
import com.kextor.kmenu.KMenuBar
import com.kextor.kmenu.KMenuItems
import com.kextor.ktabs.KTabbedPane
import com.kextor.utils.GetResources
import java.awt.Color
import java.awt.Dimension
import java.awt.Event
import java.awt.Toolkit
import java.awt.event.KeyEvent
import java.lang.reflect.Field
import javax.swing.JFrame
import javax.swing.UIManager

/**
 * Class that init GUI
 *
 * @author Daniel Córdova A.
 */
class KextorInitGUI {

    companion object {
        private const val APP_TITLE = "Kextor"
    }

    private val tabbedPane = KTabbedPane()
    private var backgroundColor = Color(150, 155, 155)

    fun createAndShowKextorGUI(iconTheme: String?) {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
        try {
            val xToolkit: Toolkit = Toolkit.getDefaultToolkit()
            println("XTOOLKIT $xToolkit")
            val awtAppClassNameField: Field = xToolkit.javaClass.getDeclaredField("awtAppClassName")
            awtAppClassNameField.isAccessible = true
            awtAppClassNameField.set(xToolkit, APP_TITLE)
        } catch (e: Exception) {
            // ignore
            System.err.println(e.message)
        }

        val kextorFrame = JFrame(APP_TITLE)
        kextorFrame.iconImage = GetResources.getIconOrSplashTheme(iconTheme)
        kextorFrame.background = backgroundColor
        kextorFrame.contentPane.background = backgroundColor
        kextorFrame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        kextorFrame.jMenuBar = createMenus()
        kextorFrame.contentPane.add(tabbedPane)
        kextorFrame.pack()
        kextorFrame.extendedState = JFrame.MAXIMIZED_BOTH
        if (kextorFrame.width < 800) {
            kextorFrame.size = Dimension(800, 600)
        }
        kextorFrame.isVisible = true
    }

    private fun createMenus(): KMenuBar {
        val menuItem: HashMap<String, List<KMenuItems>> = HashMap()

        val open = { openCommand(tabbedPane) }
        val new = { newCommand(tabbedPane) }
        val save = { saveCommand(tabbedPane) }
        val saveAs = { saveAsCommand(tabbedPane) }
        val close = { closeCommand(tabbedPane) }

        val subMenus: List<KMenuItems> = listOf(
            KMenuItems("Open", KeyEvent.VK_O, Event.CTRL_MASK, open),
            KMenuItems("New", KeyEvent.VK_N, Event.CTRL_MASK, new),
            KMenuItems("Save", KeyEvent.VK_S, Event.CTRL_MASK, save),
            KMenuItems("Save As", KeyEvent.VK_S, Event.CTRL_MASK + Event.SHIFT_MASK, saveAs),
            KMenuItems("Close", KeyEvent.VK_W, Event.CTRL_MASK, close)
        )
        menuItem["File"] = subMenus

        return KMenu().createMenu(menuItem)
    }

    fun setBackgroundColor(color: Color) {
        backgroundColor = color
    }
}
