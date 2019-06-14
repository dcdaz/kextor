import com.kextor.utils.PropertyManager
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.InputStream

/**
 * Test for Property manager
 *
 * @author Daniel Córdova A.
 */
object PropertyManagerTest {

    private const val PROPERTIES_NAME = "Kextor-test.properties"
    private val propertyManager: PropertyManager = PropertyManager()

    @BeforeAll
    @JvmStatic
    fun setUp() {
        val inputStream: InputStream = this::class.java.getResourceAsStream("/$PROPERTIES_NAME")
        propertyManager.loadAppProps(inputStream)
    }

    @Test
    fun shouldReturnAStoredProperty() {
        assertEquals("1", propertyManager.getProperty("test.value"))
    }

    @Test
    fun shouldSetAProperty() {
        propertyManager.setProperty("new.property", "New Value")
        assertEquals("New Value", propertyManager.getProperty("new.property"))
    }

    @Test
    fun shouldUnsetAProperty() {
        propertyManager.unsetProperty("new.property")
        assertEquals("", propertyManager.getProperty("new.property"))
    }

    @Test
    fun shouldResetAProperty() {
        propertyManager.resetProperty("new.property")
        assertEquals(null, propertyManager.getProperty("new.property"))
    }
}
