package com.movile.kotlin.commons.serialization

import com.fasterxml.jackson.databind.BeanDescription
import com.fasterxml.jackson.databind.DeserializationConfig
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.KeyDeserializer
import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.databind.SerializationConfig
import com.fasterxml.jackson.databind.deser.Deserializers
import com.fasterxml.jackson.databind.deser.KeyDeserializers
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.Serializers
import com.fasterxml.jackson.databind.type.TypeFactory

class CommonModule : SimpleModule() {

    override fun setupModule(context: Module.SetupContext) {
        context.addSerializers(CommonSerializers(context.typeFactory))
        context.addDeserializers(CommonDeserializers())
        context.addKeyDeserializers(CommonKeyDeserializers())

        super.setupModule(context)
    }
}

class CommonSerializers(private val typeFactory: TypeFactory) : Serializers.Base() {

    override fun findSerializer(
        config: SerializationConfig,
        type: JavaType,
        beanDesc: BeanDescription
    ): JsonSerializer<*>? =
        when (type.rawClass) {
            else -> super.findSerializer(config, type, beanDesc)
        }
}

class CommonDeserializers : Deserializers.Base() {

    @Throws(JsonMappingException::class)
    override fun findBeanDeserializer(
        type: JavaType,
        config: DeserializationConfig,
        beanDesc: BeanDescription
    ): JsonDeserializer<*>? =
        when (type.rawClass) {
//            SensitiveString::class.java -> SensitiveStringDeserializer
//            SimpleLocale::class.java -> SimpleLocaleDeserializer
//            TimeZone::class.java -> TimezoneDeserializer()
//            Option::class.java -> OptionDeserializer<Any>(type.bindings.typeParameters[0])
//            Sensitive::class.java -> SensitiveDeserializer<Any>(type.bindings.typeParameters[0])
            else -> super.findBeanDeserializer(type, config, beanDesc)
        }
}

class CommonKeyDeserializers : KeyDeserializers {

    @Throws(JsonMappingException::class)
    override fun findKeyDeserializer(
        type: JavaType,
        config: DeserializationConfig,
        beanDesc: BeanDescription
    ): KeyDeserializer? =
        when (type.rawClass) {
//            SimpleLocale::class.java -> SimpleLocaleKeyDeserializer
            else -> null
        }
}
