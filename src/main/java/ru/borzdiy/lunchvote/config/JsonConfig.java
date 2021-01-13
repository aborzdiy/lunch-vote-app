package ru.borzdiy.lunchvote.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JsonConfig {

    /**
     * <p>
     * Handling Hibernate lazy-loading
     *
     * @link https://github.com/FasterXML/jackson
     * @link https://github.com/FasterXML/jackson-datatype-hibernate
     * @link https://github.com/FasterXML/jackson-docs/wiki/JacksonHowToCustomSerializers
     */

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        JavaTimeModule timeModule = new JavaTimeModule();
        Hibernate5Module hibernate5Module = new Hibernate5Module();

        return new ObjectMapper()
                .registerModules(timeModule, hibernate5Module)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
}
