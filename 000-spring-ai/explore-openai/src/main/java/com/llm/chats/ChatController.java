package com.llm.chats;

import com.llm.dto.UserInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private static Logger logger = LoggerFactory.getLogger(ChatController.class);

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @PostMapping("/v1/chats")
    public Object chat(@RequestBody UserInput userInput) {
        logger.info("Received user input: {}", userInput);

        var chatClientRequestSpec = chatClient
                .prompt()
                .user(userInput.prompt());

        logger.info("Constructed chat client request spec: {}", chatClientRequestSpec);

        var responseSpec = chatClientRequestSpec.call();

       var response = responseSpec.content();
        logger.info("Received response spec: {}", response);

        return response;
    }
}
