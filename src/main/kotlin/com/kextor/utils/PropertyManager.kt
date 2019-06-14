package com.kextor.utils

import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.io.Reader
import java.util.*


/**
 * Class that handle all properties for Kextor
 *
 * @author Daniel Córdova A.
 */
class PropertyManager {

    private val appProperties = Properties()
    private val userProperties = Properties()
    private val localizationProperties = Properties()

    @Throws(IOException::class)
    fun loadAppProps(inputStream: InputStream) {
        loadProps(appProperties, inputStream)
    }

    @Throws(IOException::class)
    fun loadLocalizationProps(reader: Reader?) {
        if (reader == null)
            localizationProperties.clear()
        else
            loadProps(localizationProperties, reader)
    }

    @Throws(IOException::class)
    fun loadUserProps(reader: Reader) {
        loadProps(userProperties, reader)
    }

    @Throws(IOException::class)
    fun saveUserProps(outputStream: OutputStream) {
        TODO("Add a way to store user Properties on kextor config folder")
    }

    @Throws(IOException::class)
    private fun loadProps(currentProperties: Properties, currentInputStream: InputStream) {
        currentInputStream.use { inputStream ->
            currentProperties.load(inputStream)
        }
    }

    @Throws(IOException::class)
    private fun loadProps(currentProperties: Properties, currentReader: Reader) {
        currentReader.use { reader ->
            currentProperties.load(reader)
        }
    }

    fun getProperty(name: String): String? {
        var value: String? = userProperties.getProperty(name)
        if (value != null)
            return value

        value = localizationProperties.getProperty(name)
        return value ?: getDefaultProperty(name)

    }

    fun setProperty(name: String, value: String?) {
        val property: String? = getDefaultProperty(name)

        /* if value is null:
		 * - if default is null, unset userProperties prop
		 * - else set userProperties prop to ""
		 * else
		 * - if default equals value, ignore
		 * - if default doesn't equal value, set userProperties
		 */
        if (value == null) {
            if (property.isNullOrEmpty())
                userProperties.remove(name)
            else
                userProperties.setProperty(name, "")
        } else {
            if (value == property)
                userProperties.remove(name)
            else
                userProperties.setProperty(name, value)
        }
    }

    fun unsetProperty(name: String) {
        if (getDefaultProperty(name) != null)
            userProperties.setProperty(name, "")
        else
            userProperties.remove(name)
    }

    fun resetProperty(name: String) {
        userProperties.remove(name)
    }

    private fun getDefaultProperty(name: String): String? {
        val value: String? = userProperties.getProperty(name)
        if (value != null)
            return value

        return appProperties.getProperty(name)
    }
}
