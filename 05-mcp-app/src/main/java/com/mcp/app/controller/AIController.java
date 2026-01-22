package com.mcp.app.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    private ChatClient chatClient;

    public AIController(ChatClient.Builder builder, ToolCallbackProvider toolCallbackProvider) {
        this.chatClient = builder
                .defaultToolCallbacks(toolCallbackProvider)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    @RequestMapping("/chat")
    public ResponseEntity<String> getAIResponse(@RequestParam(value = "q", required = true) String message) {
        // Use chatClient to get response from AI model
        String response = chatClient.prompt(message).call().content();
        return ResponseEntity.ok(response);
    }
}
