package com.spring.ollama.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class ChatController {

    private ChatClient chatClient;

    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @RequestMapping("/chat")
    public ResponseEntity<String> chat(@RequestParam(value = "q", required = true) String message) {
        final String response = chatClient.prompt(message).call().content();
        return ResponseEntity.ok(response);
    }
}
