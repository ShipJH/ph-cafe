package ph.cafe.io.config

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.BeanProperty
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.deser.ContextualDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import org.springframework.boot.jackson.JsonComponent
import ph.cafe.io.common.Constants.LOCALDATETIME_FORMATTER
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class JacksonDeserializerConfig {

    @JsonComponent
    class CustomLocalDateTimeDeserializer : JsonDeserializer<LocalDateTime>(), ContextualDeserializer {

        override fun createContextual(ctxt: DeserializationContext, property: BeanProperty?): JsonDeserializer<*> {
            property?.let { beanProperty ->
                beanProperty.getAnnotation(JsonFormat::class.java)?.let { jsonFormat ->
                    if (jsonFormat.pattern.isNotBlank()) {
                        return LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(jsonFormat.pattern))
                    }
                }
            }
            return this
        }
        override fun deserialize(p: JsonParser, ctxt: DeserializationContext): LocalDateTime {
            return LocalDateTime.parse(p.readValueAs(String::class.java), LOCALDATETIME_FORMATTER)
        }
    }
}