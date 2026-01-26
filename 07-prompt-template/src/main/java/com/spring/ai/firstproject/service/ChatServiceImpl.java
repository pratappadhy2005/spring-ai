package com.spring.ai.firstproject.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

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

    @Override
    public String chatTemplate(String message) {

        /**
         *
        //First step: Create a PromptTemplate
        PromptTemplate template = PromptTemplate.builder()
                .template("As an expert in coding  and programming. Always write program in {tech name} Now reply for this question: {message}")
                .build();

        //Second step: Render the template with actual values
        String rendererMessage = template.render(Map.of(
                "techName","Java",
                "exampleName", "Spring AI"
                ,"message", message
        ));

        //Third step: Create a Prompt with the rendered message
        Prompt prompt = new Prompt(rendererMessage);

         **/

        SystemPromptTemplate systemPrompt = SystemPromptTemplate.builder()
                .template("You are a helpful assistant that helps people find information.")
                .build();
        var systemMessage = systemPrompt.createMessage();

        PromptTemplate userTemplate = PromptTemplate.builder()
                .template("What is the {techname} ? Tell me a little intro about {techExample}")
                .build();
        var userMessage = userTemplate.createMessage(Map.of(
                "techname", "Java",
                "techExample", "Spring AI"
        ));

        Prompt finalPrompt = new Prompt(systemMessage, userMessage);

        return this.chatClient
                .prompt(finalPrompt)
                .call()
                .content();
    }
}
