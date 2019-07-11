package com.kextor.ui

import com.kextor.utils.GetResources
import java.awt.Color
import java.awt.Font
import java.awt.Image
import javax.swing.*


/** Class that displays Splash Screen at startup
 *
 * @author Daniel Córdova A.
 */
class KextorSplashScreen(splashImageName: String?, iconTheme: String?): JFrame() {

    private lateinit var textLabel: JLabel
    private val backgroundColor: Color =
        if (splashImageName.isNullOrBlank() || splashImageName.contains("dark"))
            SPLASH_BACKGROUND_DARK else SPLASH_BACKGROUND_LIGHT

    companion object {
        private const val SPLASH_WIDTH = 600
        private const val SPLASH_HEIGHT = 295
        private const val SPLASH_IMAGE_HEIGHT = 270
        private val SPLASH_BACKGROUND_DARK = Color(52, 52, 52, 255)
        private val SPLASH_BACKGROUND_LIGHT = Color(254, 254, 254, 255)
        private val SPLASH_TEXT_FOREGROUND = Color(233, 120, 62, 255)
        private val SPLASH_PROGRESS_BAR_FOREGROUND = Color(100, 118, 251, 255)
    }

    init {
        val splashImage: Image = GetResources.getIconOrSplashTheme(splashImageName, false)
        val progressBar: JProgressBar = createProgressBar()
        this.iconImage = GetResources.getIconOrSplashTheme(iconTheme)
        this.title = "Kextor" // TODO Extract App Title To allow use the same name on splas and main window
        initializeSplashScreen()
        addSplashImage(splashImage)
        initTextLabel()
        textLabel.text = "Initializing Kextor"
        addProgressBar(progressBar)
        runProgressBar(progressBar)
        Thread.sleep(4000)
        this.dispose()
    }

    private fun initializeSplashScreen() {
        this.contentPane.layout = null
        this.isUndecorated = true // Turn Off title bar
        this.setSize(SPLASH_WIDTH, SPLASH_HEIGHT)
        this.setLocationRelativeTo(null) // Set location to the center of the screen
        this.contentPane.background = backgroundColor
        this.isVisible = true
    }

    private fun addSplashImage(splashImage: Image) {
        val splashImageSection = JLabel(ImageIcon(splashImage))
        splashImageSection.setSize(SPLASH_WIDTH, SPLASH_IMAGE_HEIGHT)
        this.add(splashImageSection)

    }

    private fun initTextLabel() {
        textLabel = JLabel()
        textLabel.font = Font("Default", 0, 12)
        textLabel.setBounds(0, SPLASH_IMAGE_HEIGHT, SPLASH_WIDTH,15)
        textLabel.foreground = SPLASH_TEXT_FOREGROUND
        textLabel.horizontalAlignment = SwingConstants.CENTER
        this.add(textLabel)
    }

    private fun createProgressBar(): JProgressBar {
        val progressBar = JProgressBar()
        progressBar.setBounds(0, 290, SPLASH_WIDTH, 4)
        progressBar.isBorderPainted = false
        progressBar.isStringPainted = false
        progressBar.background = backgroundColor
        progressBar.foreground = SPLASH_PROGRESS_BAR_FOREGROUND
        progressBar.value = 0
        return progressBar
    }

    private fun addProgressBar(progressBar: JProgressBar) {
        this.add(progressBar)
    }

    private fun runProgressBar(progressBar: JProgressBar) {
        var i = 0
        while (i <= 100) {
            try {
                Thread.sleep(20)
                progressBar.value = i
                i++
            } catch (e: Exception) {
                System.err.println("Error during initialization ${e.message}")
            }
        }
    }
}
