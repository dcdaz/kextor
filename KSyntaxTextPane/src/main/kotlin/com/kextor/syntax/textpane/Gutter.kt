package com.kextor.syntax.textpane

import java.awt.*
import java.awt.Color
import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener
import java.util.*
import javax.swing.JTextArea
import javax.swing.JTextPane
import javax.swing.SwingUtilities
import javax.swing.border.CompoundBorder
import javax.swing.border.EmptyBorder
import javax.swing.border.MatteBorder
import javax.swing.event.CaretEvent
import javax.swing.event.CaretListener
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener
import javax.swing.text.*

/**
 * Class that implements a Numbered bar on the left side of a SyntaxTextPane
 *
 * @author Daniel Córdova A.
 */
class Gutter(
    private val kextorTextPane: JTextPane,
    private val minimumWidth: Int = 2
): JTextArea(),
    CaretListener,
    DocumentListener,
    PropertyChangeListener {

    private var lastLine: Int = 0
    private var lastDigits: Int = 0
    private var lastHeight: Int = 0
    private var updateFont: Boolean = false
    private var gutterBackgroundColor: Color = Color.LIGHT_GRAY
    private var lineForegroundColor: Color = Color.BLACK
    private var currentLineForegroundColor: Color = Color.WHITE
    private var fonts: HashMap<String, FontMetrics>? = null

    init {
        kextorTextPane.font.deriveFont(10.0f)
        font = kextorTextPane.font
        isEditable = false
        setBorderGap()
        setPreferredWidth()
        kextorTextPane.document.addDocumentListener(this)
        kextorTextPane.addCaretListener(this)
        kextorTextPane.addPropertyChangeListener("font", this)
    }

    override fun paintComponent(graphics: Graphics?) {
        super.paintComponent(graphics)
        background = gutterBackgroundColor
        foreground = lineForegroundColor
        val fontMetrics: FontMetrics = kextorTextPane.getFontMetrics(kextorTextPane.font)
        val availableWidth: Int = size.width - insets.left - insets.right
        val clip: Rectangle? = graphics?.clipBounds
        var rowStartOffset: Int = kextorTextPane.viewToModel(Point(0, clip!!.y))
        val endOffset: Int = kextorTextPane.viewToModel(Point(0, clip.y + clip.height))

        while (rowStartOffset <= endOffset) {
            try {
                if (isCurrentLine(rowStartOffset))
                    graphics.color = currentLineForegroundColor
                else
                    graphics.color = foreground

                val lineNumber: String = getTextLineNumber(rowStartOffset)
                val stringWidth: Int = fontMetrics.stringWidth(lineNumber)
                val offsetX: Int = getOffsetX(availableWidth, stringWidth) + insets.left
                val offsetY: Int = getOffsetY(rowStartOffset, fontMetrics)
                graphics.drawString(lineNumber, offsetX, offsetY)
                rowStartOffset = Utilities.getRowEnd(kextorTextPane, rowStartOffset) + 1
            } catch (e: Exception) {
                break
            }

        }
    }

    private fun getTextLineNumber(rowStartOffset: Int): String {
        val root: Element = kextorTextPane.document.defaultRootElement
        val index: Int = root.getElementIndex(rowStartOffset)
        val line: Element = root.getElement(index)

        return if (line.startOffset == rowStartOffset)
            (index + 1).toString()
        else
            ""
    }

    private fun getOffsetX(availableWidth: Int, stringWidth: Int): Int {
        return availableWidth - stringWidth
    }

    @Throws(BadLocationException::class)
    private fun getOffsetY(rowStartOffset: Int, fontMetrics: FontMetrics): Int {
        val rectangle: Rectangle = kextorTextPane.modelToView(rowStartOffset)
        val lineHeight: Int = fontMetrics.height
        val y: Int = rectangle.y + rectangle.height
        var descent = 0

        if (rectangle.height == lineHeight) {
            descent = fontMetrics.descent
        } else {
            if (fonts == null)
                fonts = HashMap()

            val root: Element = kextorTextPane.document.defaultRootElement
            val index: Int = root.getElementIndex(rowStartOffset)
            val line: Element = root.getElement(index)

            for (i in 0 until line.elementCount) {
                val child: Element = line.getElement(i)
                val attributeSet: AttributeSet = child.attributes
                val fontFamily = attributeSet.getAttribute(StyleConstants.FontFamily) as String
                val fontSize = attributeSet.getAttribute(StyleConstants.FontSize) as Int
                val key: String = fontFamily + fontSize
                var maybeFontMetrics: FontMetrics? = fonts!![key]

                if (maybeFontMetrics == null) {
                    val font = Font(fontFamily, Font.PLAIN, fontSize)
                    maybeFontMetrics = kextorTextPane.getFontMetrics(font)
                    fonts!![key] = maybeFontMetrics
                }
                descent = Math.max(descent, maybeFontMetrics!!.descent)
            }
        }
        return y - descent
    }

    private fun isCurrentLine(rowStartOffset: Int): Boolean {
        val caretPosition: Int = kextorTextPane.caretPosition
        val root: Element = kextorTextPane.document.defaultRootElement
        return root.getElementIndex(rowStartOffset) == root.getElementIndex(caretPosition)
    }

    /**
     *  Calculate the width needed to display the maximum line number
     */
    private fun setPreferredWidth() {
        val root: Element = kextorTextPane.document.defaultRootElement
        val lines: Int = root.elementCount
        val digits: Int = Math.max(lines.toString().length, minimumWidth)
        val fontMetrics: FontMetrics = getFontMetrics(font)
        val width: Int = fontMetrics.charWidth('0') * digits
        val preferredWidth: Int = insets.left + insets.right + width
        val dimension: Dimension = preferredSize
        dimension.setSize(preferredWidth, HEIGHT)
        preferredSize = dimension
        size = dimension
    }

    /**
     * The border gap used to calculate left and right insets of the border
     */
    private fun setBorderGap() {
        val inner = EmptyBorder(0, BORDER_GAP, 0, BORDER_GAP)
        border = CompoundBorder(OUTER, inner)
        lastDigits = 0
    }

    fun setGutterBackgroundColor(color: Color) {
        gutterBackgroundColor = color
    }

    fun setLineForegroundColor(color: Color) {
        lineForegroundColor = color
    }

    /**
     * Allow to change color of current line number
     */
    fun setCurrentLineForegroundColor(color: Color) {
        currentLineForegroundColor = color
    }

    /**
     *  Set the update font property. Indicates whether this Font should be
     *  updated automatically when the Font of the related text component
     *  is changed.
     * */
    fun setUpdateFont(isUpdateFont: Boolean) {
        updateFont = isUpdateFont
    }

    private fun documentChanged() {
        SwingUtilities.invokeLater {
            try {
                val endPosition: Int = kextorTextPane.document.length
                val rectangle: Rectangle? = kextorTextPane.modelToView(endPosition)

                if (rectangle != null && rectangle.y != lastHeight) {
                    setPreferredWidth()
                    repaint()
                    lastHeight = rectangle.y
                }
            } catch (ex: BadLocationException) {}
        }
    }

    /**
     * Override Listeners
     */

    override fun changedUpdate(documentEvent: DocumentEvent) {
        documentChanged()
    }

    override fun insertUpdate(documentEvent: DocumentEvent) {
        documentChanged()
    }

    override fun removeUpdate(documentEvent: DocumentEvent) {
        documentChanged()
    }

    override fun caretUpdate(caretEvent: CaretEvent) {
        val caretPosition: Int = kextorTextPane.caretPosition
        val root: Element = kextorTextPane.document.defaultRootElement
        val currentLine: Int = root.getElementIndex(caretPosition)
        if (lastLine != currentLine) {
            repaint()
            lastLine = currentLine
        }
    }

    override fun propertyChange(propertyChangeEvent: PropertyChangeEvent) {
        if (propertyChangeEvent.newValue is Font) {
            if (updateFont) {
                val newFont = propertyChangeEvent.newValue as Font
                font = newFont
                lastDigits = 0
                setPreferredWidth()
            } else {
                repaint()
            }
        }
    }

    companion object {
        private const val HEIGHT: Int = Integer.MAX_VALUE - 1000000
        private const val BORDER_GAP = 3
        private val OUTER = MatteBorder(0, 0, 0, 2, Color.GRAY)
    }
}
