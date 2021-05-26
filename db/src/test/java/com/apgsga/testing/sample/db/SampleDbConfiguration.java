package com.apgsga.testing.sample.db;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jdbc.repository.config.JdbcRepositoryConfigExtension;
import org.springframework.data.relational.core.mapping.event.RelationalEvent;

@Configuration
@EnableJdbcRepositories
public class SampleDbConfiguration extends AbstractJdbcConfiguration {

    /**
     * @return {@link ApplicationListener} for {@link RelationalEvent}s.
     */
    @Bean
    public ApplicationListener<?> loggingListener() {

        return (ApplicationListener<ApplicationEvent>) event -> {
            if (event instanceof RelationalEvent) {
                System.out.println("Received an event: " + event);
            }
        };
    }
}
