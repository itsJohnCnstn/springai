package com.johncnstn.springai.controller;

import com.johncnstn.springai.tool.DateTimeTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChatController {

    private final ChatClient chatClient;
    private final ChatClient factualChat;

    public ChatController(@Qualifier("chat") ChatClient chatClient,
                          @Qualifier("factualChat") ChatClient factualChat) {
        this.chatClient = chatClient;
        this.factualChat = factualChat;
    }

    /**
     * Processes a user message using the chat client.
     *
     * <p>Example usage: {@code http localhost:8080 message="What day is tomorrow?"}
     *
     * @param message the user message to process
     * @return the AI-generated response content
     */
    @PostMapping()
    public String chat(@RequestBody String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }

    /**
     * Processes a user message using the chat client with date/time tool support.
     *
     * <p>Example usage: {@code http localhost:8080/tools message="What day is tomorrow?"}
     *
     * @param message the user message to process
     * @return the AI-generated response content
     */
    @PostMapping("/tools")
    public String dateTimeTool(@RequestBody String message) {
        return chatClient.prompt()
                .user(message)
                .tools(new DateTimeTools())
                .call()
                .content();
    }


    @GetMapping()
    public String classifyTweets() {
        var system = new SystemMessage("You are an assistant that classifies tweent sentiment as Positive, Negative, or Natural.");
        var user = new UserMessage("""
                Tweet: "I love sun!"
                Sentiment: Positive
                
                Tweet: "I lost money."
                Sentiment: Negative
                
                Tweet: "Just had lunch."
                Sentiment: Netural 
                
                Tweet: "Excited for my trip!!"
                Sentiment:
                """);
        return factualChat.prompt(new Prompt(List.of(system, user)))
                .call()
                .content();
    }

}
