package com.kextor.utils

import java.awt.Image
import java.awt.image.BufferedImage
import java.io.InputStream
import javax.imageio.ImageIO
import javax.swing.ImageIcon

/**
 * Object that helps Kextor to obtain some resources to work with them
 *
 * @author Daniel Córdova A.
 */
object GetResources {

    private const val ICON_NAME: String = "kextor-logo-"
    private const val SPLASH_NAME: String = "kextor-splash-"
    private const val EXTENSION: String = ".png"

    fun getIconOrSplashTheme(iconOrSplashName: String?, isIcon: Boolean = true): Image {
        val name: String = if (iconOrSplashName.isNullOrBlank()) "dark" else iconOrSplashName
        val fullName: String = if(isIcon) "$ICON_NAME$name$EXTENSION" else "$SPLASH_NAME$name$EXTENSION"
        return getResourcesAsAnImage(fullName)
    }

    private fun getResourcesAsAnImage(imageName: String): Image {
        val kextorSplashImagePath: InputStream = this::class.java.getResourceAsStream("/$imageName")
        val bufferedImage: BufferedImage = ImageIO.read(kextorSplashImagePath)
        val splashImage = ImageIcon(bufferedImage)
        return splashImage.image
    }
}
