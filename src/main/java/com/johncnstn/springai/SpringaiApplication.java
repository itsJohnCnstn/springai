package com.johncnstn.springai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringaiApplication {

    static void main(String[] args) {
        SpringApplication.run(SpringaiApplication.class, args);
    }

    @Bean
    protected ChatClient chat(ChatClient.Builder chatClient) {
        return chatClient
                .build();
    }

    @Bean
    protected ChatClient factualChat(ChatClient.Builder factualChatClient) {
        return factualChatClient
                .defaultOptions(ChatOptions.builder()
                        .temperature(0.0d)
                        .build())
                .build();
    }

}
