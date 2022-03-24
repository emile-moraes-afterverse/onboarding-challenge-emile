package com.afterverse.api

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.CollectionType
import com.fasterxml.jackson.databind.type.MapType
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.afterburner.AfterburnerModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import com.afterverse.api.JsonExtensions.DEFAULT_OBJECT_MAPPER
import com.afterverse.api.JsonExtensions.genericMapType
import com.afterverse.api.JsonExtensions.javaType
import com.afterverse.api.JsonExtensions.listType
import com.movile.kotlin.commons.serialization.CommonModule
import io.vavr.control.Try
import java.util.ArrayList
import kotlin.collections.toMap as mapCollection

/**
 * @author José Carlos Cieni Júnior (jose.cieni@movile.com)
 * @author Diego Rocha (diego.rocha@movile.com)
 */
object JsonExtensions {
    val DEFAULT_OBJECT_MAPPER = configureDefaultObjectMapper(ObjectMapper())

    fun configureDefaultObjectMapper(objectMapper: ObjectMapper): ObjectMapper {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)

        objectMapper.registerModule(Jdk8Module())
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.registerModule(ParameterNamesModule())
        objectMapper.registerModule(AfterburnerModule().setUseValueClassLoader(false))
        objectMapper.registerKotlinModule()
        objectMapper.registerModule(CommonModule())

        return objectMapper
    }

    fun javaType(objectMapper: ObjectMapper, clazz: Class<*>): JavaType =
        objectMapper.typeFactory.constructType(clazz)

    fun listType(objectMapper: ObjectMapper, clazz: Class<*>): CollectionType =
        objectMapper.typeFactory.constructCollectionType(ArrayList::class.java, clazz)

    fun mapType(objectMapper: ObjectMapper, keyClass: Class<*>, valueClass: Class<*>): MapType =
        genericMapType<HashMap<*, *>>(objectMapper, keyClass, valueClass)

    inline fun <reified T: Map<*, *>> genericMapType(objectMapper: ObjectMapper,
                                                     keyClass: Class<*>,
                                                     valueClass: Class<*>): MapType =
        objectMapper.typeFactory.constructMapType(T::class.java, keyClass, valueClass)
}

// Serialization
fun Any.toJson(objectMapper: ObjectMapper): Try<String> =
    Try.ofSupplier { objectMapper.writeValueAsString(this) }

fun Any.toJson(): Try<String> = this.toJson(DEFAULT_OBJECT_MAPPER)

fun Any.getJsonOrThrow(): String = this.toJson().getOrElseThrow{ e -> IllegalArgumentException(e) }

inline fun <reified K : Any, reified V : Any>  Any.toMap(objectMapper: ObjectMapper): Try<Map<K, V>> =
    Try.ofSupplier {
        objectMapper
            .convertValue<Map<K, V>>(this, genericMapType<HashMap<K, V>>(objectMapper, K::class.java, V::class.java))
    }

inline fun <reified K : Any, reified V : Any>  Any.toMap(): Try<Map<K, V>> = this.toMap(DEFAULT_OBJECT_MAPPER)

// Deserialization
fun <T> String.fromJson(objectMapper: ObjectMapper, javaType: JavaType): Try<T> =
    Try.ofSupplier { objectMapper.readValue<T>(this, javaType) }

inline fun <reified T : Any> String.fromJson(objectMapper: ObjectMapper): Try<T> =
    this.fromJson(objectMapper, javaType(objectMapper, T::class.java))

inline fun <reified T : Any> String.fromJson(): Try<T> =
    this.fromJson(DEFAULT_OBJECT_MAPPER)

inline fun <reified T : Any> String.fromJsonList(objectMapper: ObjectMapper): Try<List<T>> =
    this.fromJson(objectMapper, listType(objectMapper, T::class.java))

inline fun <reified T : Any> String.fromJsonList(): Try<List<T>> =
    this.fromJsonList(DEFAULT_OBJECT_MAPPER)

@Deprecated("This method is deprecated, use 'fromJsonToMap' instead", ReplaceWith("fromJsonToMap()"))
inline fun <reified K: Any, reified V: Any> String.fromJsonMap(): Try<Map<K, V>> =
    this.fromJsonToMap(false)

inline fun <reified K: Any, reified V: Any> String.fromJsonToMap(objectMapper: ObjectMapper,
                                                                 keepInsertionOrder: Boolean = false): Try<Map<K, V>> {
    val mapType =
        if (keepInsertionOrder) {
            genericMapType<LinkedHashMap<K, V>>(objectMapper, K::class.java, V::class.java)
        } else {
            genericMapType<HashMap<K, V>>(objectMapper, K::class.java, V::class.java)
        }

    return this.fromJson(objectMapper, mapType)
}

inline fun <reified K: Any, reified V: Any> String.fromJsonToSensitiveMap(
    objectMapper: ObjectMapper,
    valueClass: Class<*>
): Try<Map<K, V>> {
    val mapType = genericMapType<HashMap<K, V>>(objectMapper, K::class.java, valueClass)
    return this.fromJson(objectMapper, mapType)
}

inline fun <reified K: Any, reified V: Any> String.fromJsonToMap(keepInsertionOrder: Boolean = false): Try<Map<K, V>> =
    this.fromJsonToMap(DEFAULT_OBJECT_MAPPER, keepInsertionOrder)

inline fun <reified K: Any, reified V: Any> String.fromJsonToSensitiveMap(): Try<Map<K, Sensitive<V>>> =
    this.fromJsonToSensitiveMap<K, V>(DEFAULT_OBJECT_MAPPER, V::class.java).mapTry { map ->
        map.entries.map { it.key to it.value.toSensitive() }.mapCollection()
    }

inline fun <reified T: Any> Map<String, Any>.fromMap(objectMapper: ObjectMapper): Try<T> =
    Try.ofSupplier { objectMapper.convertValue<T>(this, javaType(objectMapper, T::class.java)) }

inline fun <reified T: Any> Map<String, Any>.fromMap(): Try<T> =
    this.fromMap(DEFAULT_OBJECT_MAPPER)
