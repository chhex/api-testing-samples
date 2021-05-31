package com.apgsga.testing.sample.client;

import com.apgsga.testing.sample.api.PersonManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.TransactionManager;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@Configuration
public class ClientBuilder {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    public static PersonManagerService build(String[] args) {
        // TODO (che, 31.1) Proper Commandline Option Parser
        String profile = args.length > 0 ? args[0] : "rest";
        String baseUrl  = args.length > 1 ? args[1] :  "http://localhost:8080";
        Logger LOGGER = LoggerFactory.getLogger(ClientBuilder.class);
        LOGGER.info(String.format("Running Client with profile=%s and baseUrl:%s",profile, baseUrl));
        ConfigurableApplicationContext context = new SpringApplicationBuilder(ClientBuilder.class)
                .web(WebApplicationType.NONE)
                .run(String.format("--spring.profiles.active=%s",profile), String.format("--baseUrl=%s",baseUrl));
        return context.getBean(PersonManagerService.class);
    }
    @Profile("rest")
    @ComponentScan(basePackages = { "com.apgsga.testing.sample.client.rest"})
    class RestConfiguration {

    }

    @Configuration
    @Profile("direct")
    @ComponentScan(basePackages = {"com.apgsga.testing.sample.service", "com.apgsga.testing.sample.db"})
    @EnableJdbcRepositories(basePackages = {"com.apgsga.testing.sample.db"})
    class DirectConfiguration extends AbstractJdbcConfiguration {

        @Bean
        public DataSource dataSource() {
            EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
            builder.addScript("sql/schema.sql");
            return builder.setType(EmbeddedDatabaseType.H2).build();
        }

        @Bean
        NamedParameterJdbcOperations namedParameterJdbcOperations(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }

        @Bean
        TransactionManager transactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }
    }
}
