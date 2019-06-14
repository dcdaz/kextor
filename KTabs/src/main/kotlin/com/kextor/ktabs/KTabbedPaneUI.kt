package com.kextor.ktabs

import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.util.*
import javax.swing.SwingConstants
import javax.swing.UIManager
import javax.swing.plaf.basic.BasicGraphicsUtils
import javax.swing.plaf.basic.BasicTabbedPaneUI
import javax.swing.text.View

/**
 * Class that implements a customizable TabbedPaneUI
 *
 * @author Daniel Córdova A.
 */
class KTabbedPaneUI : BasicTabbedPaneUI() {

    private var selectColor: Color? = null
    private var deSelectColor: Color? = null
    private val inclTab = 4
    private val anchoFocoH = 4
    private val anchoCarpetas = 18
    private var closeButton: Rectangle? = null
    private lateinit var shape: Polygon

    override fun installDefaults() {
        super.installDefaults()
        selectColor = Color(190, 190, 190)
        deSelectColor = Color(90, 90, 90)
        tabAreaInsets.right = anchoCarpetas
    }

    override fun installListeners() {
        tabPane.addMouseListener(MyMouseHandler())
        super.installListeners()
    }

    override fun paintTabArea(g: Graphics, tabPlacement: Int, selectedIndex: Int) {
        if (runCount > 1) {
            val lines = IntArray(runCount)
            for (i: Int in 0 until runCount) {
                lines[i] = rects[tabRuns[i]].y + maxTabHeight
            }
            Arrays.sort(lines)
                var row: Int = runCount
                var i = 0
                while (i < lines.size - 1) {
                    val polygon = Polygon()
                    polygon.addPoint(0, lines[i])
                    polygon.addPoint(tabPane.width - 2 * row - 2, lines[i])
                    polygon.addPoint(tabPane.width - 2 * row, lines[i] + 3)
                    if (i < lines.size - 2) {
                        polygon.addPoint(tabPane.width - 2 * row, lines[i + 1])
                        polygon.addPoint(0, lines[i + 1])
                    } else {
                        polygon.addPoint(tabPane.width - 2 * row, lines[i] + rects[selectedIndex].height)
                        polygon.addPoint(0, lines[i] + rects[selectedIndex].height)
                    }
                    polygon.addPoint(0, lines[i])
                    g.fillPolygon(polygon)
                    g.color = darkShadow.darker()
                    g.drawPolygon(polygon)
                    i++
                    row--
                }
        }
        super.paintTabArea(g, tabPlacement, selectedIndex)
    }

    override fun paintTabBackground(g: Graphics, tabPlacement: Int, tabIndex: Int, x: Int, y: Int, w: Int, h: Int, isSelected: Boolean) {
        val graphics2D = g as Graphics2D
        val xp: IntArray = intArrayOf(x, x, x + w, x + w, x)
        val yp: IntArray = intArrayOf(y, y + h - 3, y + h - 3, y, y)
        shape = Polygon(xp, yp, xp.size)

        if (isSelected) {
            graphics2D.color = selectColor
        } else {
            if (tabPane.isEnabled && tabPane.isEnabledAt(tabIndex)) {
                graphics2D.color = deSelectColor
            }
        }
        graphics2D.fill(shape)
    }

    override fun calculateTabWidth(tabPlacement: Int, tabIndex: Int, metrics: FontMetrics): Int {
        return 10 + inclTab + super.calculateTabWidth(tabPlacement, tabIndex, metrics)
    }

    override fun calculateTabHeight(tabPlacement: Int, tabIndex: Int, fontHeight: Int): Int {
        return if (tabPlacement == SwingConstants.LEFT || tabPlacement == SwingConstants.RIGHT) {
            super.calculateTabHeight(tabPlacement, tabIndex, fontHeight)
        } else {
            anchoFocoH + super.calculateTabHeight(tabPlacement, tabIndex, fontHeight)
        }
    }

    override fun paintTabBorder(g: Graphics, tabPlacement: Int, tabIndex: Int, x: Int, y: Int, w: Int, h: Int, isSelected: Boolean) {}

    override fun paintFocusIndicator(g: Graphics, tabPlacement: Int, rects: Array<Rectangle>, tabIndex: Int, iconRect: Rectangle?, textRect: Rectangle?, isSelected: Boolean) {
        if (tabPane.hasFocus() && isSelected) {
            g.color = UIManager.getColor("ScrollBar.thumbShadow")
            g.drawPolygon(shape)
        }
    }

    /**
     * Close Button
     */
    override fun paintTab(
        graphics: Graphics,
        tabPlacement: Int,
        arrayOfRectangles: Array<Rectangle>,
        tabIndex: Int,
        iconRect: Rectangle,
        textRect: Rectangle
    ) {
        super.paintTab(graphics, tabPlacement, arrayOfRectangles, tabIndex, iconRect, textRect)
        val font: Font = graphics.font
        graphics.font = graphics.font.deriveFont(Font.BOLD, 16.0f)
        val fontMetrics: FontMetrics = graphics.getFontMetrics(graphics.font)
        val charWidth: Int = fontMetrics.charWidth('x')
        val maxAscent: Int = fontMetrics.maxAscent
        graphics.drawString(
            "x",
            textRect.x + textRect.width + 1,
            textRect.y + textRect.height - 3
        )
        closeButton = Rectangle(
            textRect.x + textRect.width - 5,
            textRect.y + textRect.height - maxAscent,
            charWidth + 2,
            maxAscent - 1
        )
        graphics.font = font
    }

    override fun paintText(g: Graphics, tabPlacement: Int, font: Font, metrics: FontMetrics, tabIndex: Int, title: String?, textRect: Rectangle, isSelected: Boolean) {
        super.paintText(g, tabPlacement, font, metrics, tabIndex, title, textRect, isSelected)
        g.font = font
        val view: View? = getTextViewForTab(tabIndex)
        if (view != null) {
            // html
            view.paint(g, textRect)
        } else {
            // plain text
            val mnemIndex: Int = tabPane.getDisplayedMnemonicIndexAt(tabIndex)
            if (tabPane.isEnabled && tabPane.isEnabledAt(tabIndex)) {
                g.color = tabPane.getForegroundAt(tabIndex)
                BasicGraphicsUtils.drawStringUnderlineCharAt(g, title, mnemIndex, textRect.x, textRect.y + metrics.ascent)
            } else { // tab disabled
                g.color = Color.BLACK
                BasicGraphicsUtils.drawStringUnderlineCharAt(g, title, mnemIndex, textRect.x, textRect.y + metrics.ascent)
                g.color = tabPane.getBackgroundAt(tabIndex).darker()
                BasicGraphicsUtils.drawStringUnderlineCharAt(g, title, mnemIndex, textRect.x - 1, textRect.y + metrics.ascent - 1)
            }
        }
    }

    /**
     * Delete Borders for each tab
     */
    fun overrideContentBorderInsetsOfUI() {
        if (this.contentBorderInsets != null) {
            this.contentBorderInsets.set(0, 0, 0, 0)
        }
    }

    inner class MyMouseHandler : MouseAdapter() {
        override fun mousePressed(mouseEvent: MouseEvent) {
            if (closeButton != null) {
                if (closeButton!!.contains(mouseEvent.point)) {
                    val tabPane: KTabbedPane = mouseEvent.source as KTabbedPane
                    val tabIndex: Int = tabForCoordinate(tabPane, mouseEvent.x, mouseEvent.y)
                    tabPane.removeTabsFromPaneIndex()
                    tabPane.remove(tabIndex)
                }
            }
        }
    }
}
