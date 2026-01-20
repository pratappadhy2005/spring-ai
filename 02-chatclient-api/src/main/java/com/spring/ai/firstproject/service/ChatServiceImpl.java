package com.spring.ai.firstproject.service;

import com.spring.ai.firstproject.entity.Tut;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatClient chatClient;

    public ChatServiceImpl(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Override
    public List<Tut> chat(String message) {
        // Implement your chat logic here
        /*String content = chatClient
                .prompt()
                .user(prompt)
                .system("You are an expert in cricket.")
                .call()
                .content();*/

        String prompt = "Tell me about Virat Kohli";
        Prompt promptObj = new Prompt(message);

        List<Tut> entity = chatClient
                .prompt(promptObj)
                .call()
                .entity(new ParameterizedTypeReference<List<Tut>>() {
                });

        return entity;
    }
}
