package com.spring.ai.firstproject.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatClient chatClient;

    public ChatServiceImpl(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public String chat(String message) {

        Prompt prompt = new Prompt(message);

        String queryStr = "As an expert in coding  and programming. Always write program in JAVA Now reply for this question: {message}";

        var response = chatClient
                .prompt()
                .user(u -> u.text(queryStr).param("message", message))
                .call()
                .content();
        return response;
    }
}
