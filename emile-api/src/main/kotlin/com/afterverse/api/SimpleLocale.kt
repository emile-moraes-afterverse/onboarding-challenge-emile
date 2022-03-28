package com.afterverse.api

class SimpleLocale(language: String, country: String? = null) {

    val language = language
    val country = country

    override fun toString() =
        buildString {
            append(language)

            if (country != null) {
                append('_')
                append(country)
            }
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SimpleLocale

        if (language != other.language) return false
        if (country != other.country) return false

        return true
    }

    override fun hashCode(): Int {
        var result = language.hashCode()
        result = 31 * result + (country?.hashCode() ?: 0)
        return result
    }

    companion object {
        @JvmStatic
        fun fromLocaleString(locale: String): SimpleLocale {
            val parts = locale.split('_', limit = 2)
            return if (parts.size == 1)
                SimpleLocale(language = parts[0])
            else
                SimpleLocale(language = parts[0], country = parts[1])
        }
    }
}
