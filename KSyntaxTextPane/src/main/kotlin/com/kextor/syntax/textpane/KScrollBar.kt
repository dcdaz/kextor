package com.kextor.syntax.textpane

import java.awt.*
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JScrollBar
import javax.swing.plaf.basic.BasicScrollBarUI

/**
 * Class that handles ScrollBar for KSyntaxTextArea
 *
 * @author Daniel Córdova A.
 */
class KScrollBar(private val isVertical: Boolean = true): BasicScrollBarUI() {
    private val dimension = Dimension()

    override fun createDecreaseButton(orientation: Int): JButton {
        return object : JButton() {
            override fun getPreferredSize(): Dimension {
                return dimension
            }
        }
    }

    override fun createIncreaseButton(orientation: Int): JButton {
        return object : JButton() {
            override fun getPreferredSize(): Dimension {
                return dimension
            }
        }
    }

    override fun paintTrack(graphics: Graphics, jComponent: JComponent?, rectangle: Rectangle) {}

    override fun paintThumb(graphics: Graphics, jComponent: JComponent?, rectangle: Rectangle) {
        val graphics2D = graphics.create() as Graphics2D
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        val jScrollBar = jComponent as JScrollBar?
        jScrollBar?.background = KSyntaxTextAreaProperties.textAreaBackgroundColor
        val color: Color =
            when {
                isDragging -> KSyntaxTextAreaProperties.kScrollBarDraggingColor
                isThumbRollover -> KSyntaxTextAreaProperties.kScrollBarHoverColor
                else -> KSyntaxTextAreaProperties.kScrollBarBodyColor
            }

        graphics2D.paint = color
        if(isVertical) {
            graphics2D.fillRoundRect(rectangle.x + 5, rectangle.y, rectangle.width/3, rectangle.height, 0, 0)
            graphics2D.paint = KSyntaxTextAreaProperties.kScrollBarBorderColor
            graphics2D.drawRoundRect(rectangle.x + 5, rectangle.y, rectangle.width/3, rectangle.height, 0, 0)
        } else {
            graphics2D.fillRoundRect(rectangle.x, rectangle.y + 5, rectangle.width, rectangle.height/3, 0, 0)
            graphics2D.paint = KSyntaxTextAreaProperties.kScrollBarBorderColor
            graphics2D.drawRoundRect(rectangle.x, rectangle.y + 5, rectangle.width, rectangle.height/3, 0, 0)
        }
        graphics2D.dispose()
    }

    override fun setThumbBounds(x: Int, y: Int, width: Int, height: Int) {
        super.setThumbBounds(x, y, width, height)
        scrollbar.repaint()
    }
}
