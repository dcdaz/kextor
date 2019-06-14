package com.kextor.kmenu

/**
 * Main Class of KMenu
 *
 * @author Daniel Córdova A.
 */
class KMenu {

    fun createMenu(kMenuItems: HashMap<String, List<KMenuItems>>): KMenuBar {
        return KMenuBar(kMenuItems)
    }
}
