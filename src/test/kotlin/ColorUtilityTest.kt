import com.kextor.utils.ColorUtility
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.awt.Color

/**
 * Test for [ColorUtility]
 *
 * @author Daniel Córdova A.
 */
object ColorUtilityTest {

    private val colorUtility: ColorUtility = ColorUtility
    private val expectedColor: Color = Color(125, 125, 125, 175)


    @Test
    fun shouldCreateColorProperly() {
        val colorProperty: String? = "125, 125, 125, 175"
        val actualColor = colorUtility.getColorProperty(colorProperty)
        assertEquals(expectedColor, actualColor)
    }

    @Test
    fun shouldCreateANullColorIfPropertyLacksOneNumber() {
        val colorProperty: String? = "125, 125, 125"
        val actualColor = colorUtility.getColorProperty(colorProperty)
        assertEquals(null, actualColor)
    }

    @Test
    fun shouldCreateANullColorIfPropertyHasABadNumber() {
        val colorProperty: String? = "125, 125g, 125, 175"
        val actualColor = colorUtility.getColorProperty(colorProperty)
        assertEquals(null, actualColor)
    }
}