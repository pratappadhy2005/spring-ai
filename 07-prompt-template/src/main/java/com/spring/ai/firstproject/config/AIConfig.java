package com.spring.ai.firstproject.config;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem("You are a helpful assistant.")
                .defaultAdvisors()
                .defaultOptions(OpenAiChatOptions.builder()
                        .model("gpt-3.5-turbo")
                        .temperature(0.7)
                        .maxTokens(200).build())
                .build();
    }
}
