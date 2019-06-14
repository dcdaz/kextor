package com.kextor.utils

/**
 * A simple class that help to identifies which OS is running,
 * this will help Kextor to know how to work in different OS's
 *
 * @author Daniel Córdova A.
 */
object OSDetector {

    const val UNIX: Int = 1
    const val WIN: Int = 2
    const val MAC: Int = 3
    const val UNKNOW_OS: Int = 4

    private val runningOS: String = System.getProperty("os.name")

    fun getOsType(): Int {
        return when {
            isUnix() -> UNIX
            isWindows() -> WIN
            isMac() -> MAC
            else -> UNKNOW_OS
        }
    }


    private fun isUnix(): Boolean {
        return (
            osNameContains("nix") ||
            osNameContains("nux") ||
            osNameContains("aix")
            )
    }

    private fun isWindows(): Boolean {
        return osNameContains("win")
    }

    private fun isMac(): Boolean {
        return osNameContains("mac")
    }

    private fun osNameContains(osName: String): Boolean {
        return runningOS.contains(osName, true)
    }
}
