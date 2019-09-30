package com.arise.steiner.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ConfigurationProperties(prefix = "queue-props", ignoreUnknownFields = true)
@SuppressWarnings("unused")
public class JMSProperties   {

    private String topicName;

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    @Override
    public String toString() {
        return "JMSProperties{" +
            "topicName='" + topicName + '\'' +
            '}';
    }
}
