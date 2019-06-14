package com.kextor.ktabs

import javax.swing.JTabbedPane
import javax.swing.SwingConstants

/**
 * Class that handles some properties and some stuff around Tabbed Pane
 *
 * @author Daniel Córdova A.
 */
class KTabbedPane: JTabbedPane() {

    private var tabIndex = 0

    init {
        this.tabPlacement = SwingConstants.TOP
        val tabbedPaneUI = KTabbedPaneUI()
        this.setUI(tabbedPaneUI)
        tabbedPaneUI.overrideContentBorderInsetsOfUI()
    }

    fun addTabsToPaneIndex() {
        this.selectedIndex = tabIndex
        tabIndex += 1
    }

    fun removeTabsFromPaneIndex() {
        tabIndex -= 1
        this.selectedIndex = tabIndex
    }
}
