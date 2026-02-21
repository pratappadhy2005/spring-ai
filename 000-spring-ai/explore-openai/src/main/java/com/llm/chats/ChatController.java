package com.llm.chats;

import com.llm.dto.UserInput;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@Validated
public class ChatController {

    private static Logger logger = LoggerFactory.getLogger(ChatController.class);

    private final ChatClient chatClient;

    @Value("classpath:/prompt-templates/coding-assistant.st")
    private Resource systemText;

    public ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @PostMapping("/v1/chats")
    public Object chat(@RequestBody @Valid UserInput userInput) {
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

    @PostMapping("/v2/chats")
    public Object chatv2(@RequestBody UserInput userInput) {
        logger.info("Received user input: {}", userInput);

        var  systemMessage = """
        You are a helpful assistant who can answer JAVA based questions.
        For any other questions, you will politely decline to answer and suggest the user to ask a JAVA based question.
        """;

        var chatClientRequestSpec = chatClient
                .prompt()
                .user(userInput.prompt())
                .system(systemMessage);

        logger.info("Constructed chat client request spec: {}", chatClientRequestSpec);

        var responseSpec = chatClientRequestSpec.call();

        var response = responseSpec.content();
        logger.info("Received response spec: {}", response);

        return response;
    }

    @PostMapping("/v2/prompts/{language}")
    public Object prompts(
            @PathVariable String language,
            @RequestBody UserInput userInput) {
        logger.info("Received user input: {}", userInput);

        var chatClientRequestSpec = chatClient
                .prompt()
                .user(userInput.prompt())
                .system(promptSystemSpec -> promptSystemSpec.text(systemText).param("language", language.toUpperCase()));

        logger.info("Constructed chat client request spec: {}", chatClientRequestSpec);

        var responseSpec = chatClientRequestSpec.call();

        var response = responseSpec.content();
        logger.info("Received response spec: {}", response);

        return response;
    }

    @PostMapping("/v1/chats/stream")
    public Flux<String> chatWithStream(@RequestBody UserInput userInput) {
        return  chatClient
                .prompt()
                .user(userInput.prompt())
                .stream()
                .content()
                .doOnNext(content -> logger.info("Received content chunk: {}", content))
                .doOnError(error -> logger.error("Error during streaming response: {}", error.getMessage()))
                .doOnComplete(() -> logger.info("Completed streaming response"))
                .onErrorResume(error -> {
                    logger.error("Error during streaming response: {}", error.getMessage());
                    return Flux.just("An error occurred while processing your request. Please try again later.");
                });
    }
}
