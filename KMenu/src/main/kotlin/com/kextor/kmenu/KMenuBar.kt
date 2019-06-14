package com.kextor.kmenu

import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem
import javax.swing.KeyStroke

/**
 * Class that creates a custom JMenuBar and fill it with
 * menus, shortcuts and their own action listeners
 *
 * @author Daniel Córdova A.
 */
class KMenuBar(kMenuItems: HashMap<String, List<KMenuItems>>): JMenuBar() {

    init {
        populateMenu(kMenuItems)
    }

    private fun populateMenu(kMenuItems: HashMap<String, List<KMenuItems>>) {
        kMenuItems.forEach {
            val item = JMenu(it.key)
            this.add(item)
            val menuStuff = it.value
            menuStuff.forEach {eachKMenu ->
                val menuItem = JMenuItem(eachKMenu.name)
                menuItem.accelerator = KeyStroke.getKeyStroke(eachKMenu.key, eachKMenu.keyCommand)
                menuItem.addActionListener { eachKMenu.action.invoke() }
                item.add(menuItem)
            }
        }
    }
}

/**
 * Data class which holds data to create menus on KMenuBar
 *
 * @author Daniel Córdova A.
 */
data class KMenuItems (val name: String, val key: Int, val keyCommand: Int, val action: () -> Unit)
