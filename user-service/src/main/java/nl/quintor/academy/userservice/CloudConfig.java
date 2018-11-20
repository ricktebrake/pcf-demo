package nl.quintor.academy.userservice;

import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

@Profile(value = {"cloud"})
@Configuration
public class CloudConfig extends AbstractCloudConfig {

    @Bean
    public MongoDbFactory mongoDbFactory() {
        return connectionFactory().mongoDbFactory();
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory) {
        return new MongoTemplate(mongoDbFactory);
    }
}
