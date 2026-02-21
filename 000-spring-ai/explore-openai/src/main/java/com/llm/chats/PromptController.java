package com.llm.chats;

import com.llm.dto.UserInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PromptController {


    private static final Logger log = LoggerFactory.getLogger(PromptController.class);
    private final ChatClient chatClient;


    @Value("classpath:/prompt-templates/coding-assistant.st")
    private Resource systemText;


    public PromptController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @PostMapping("/v1/prompts")
    public String prompts(@RequestBody UserInput userInput) {
        log.info("User input received: {}", userInput);

        var  systemMessage = """
        You are a helpful assistant who can answer JAVA based questions.
        For any other questions, you will politely decline to answer and suggest the user to ask a JAVA based question.
        """;

       var sysMessage =  new SystemMessage(systemMessage);
       var userMessage = new UserMessage(userInput.prompt());

       var promptMessage = new Prompt(List.of(sysMessage,
               new UserMessage("What's my name?"),
               new AssistantMessage("I don't know"),
               new UserMessage("My name is John"),
               userMessage));

        var response = chatClient
                .prompt(promptMessage)
                .call()
                .content();

        log.info("Response received: {}", response);
        return response;
    }

}
